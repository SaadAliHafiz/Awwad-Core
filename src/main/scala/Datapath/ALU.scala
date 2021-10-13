// // package Datapath

// // import chisel3._
// // import chisel3.util._
// // import ALUOP._

// // object ALUOP {

// //     val ALU_ADD = 0.U (4.W)
// //     val ALU_SUB = 1.U (4.W)
// //     val ALU_AND = 2.U (4.W)
// //     val ALU_OR = 3.U (4.W)
// //     val ALU_XOR = 4.U (4.W)
// //     val ALU_SLT = 5.U (4.W)
// //     val ALU_SLL = 6.U (4.W)
// //     val ALU_SLTU = 7.U(4.W)
// //     val ALU_SRL = 8.U (4.W)
// //     val ALU_SRA = 9.U (4.W)
// //     val ALU_COPY_A = 10.U(4.W)
// //     val ALU_COPY_B = 11.U(4.W)
// //     val ALU_XXX = 15.U(4.W)
// // }

// // // trait Config {
// // //     val WLEN = 32
// // //     val ALUOP_SIG_LEN = 4
// // //     val XLEN = 32
// // // }

// // class ALUIO(wlen:Int, aluopSigLen:Int) extends Bundle {

// //     val in_A = Input(UInt(wlen.W))
// //     val in_B = Input(UInt(wlen.W))
// //     val alu_Op = Input(UInt(aluopSigLen.W))
// //     val out = Output(UInt(wlen.W))
// //     val branch=
// //     val sum = Output(UInt(wlen.W))
// // }

// // class Alu extends Module with Config {

// //     val io = IO (new ALUIO(WLEN,ALUOP_SIG_LEN))
// //     val sum = io.in_A + Mux(io.alu_Op(0), -io.in_B, io.in_B)
// //     val cmp = Mux (io.in_A(XLEN-1) === io.in_B(XLEN-1), sum ( XLEN-1), Mux(io.alu_Op (1), io.in_B(XLEN -1), io.in_A(XLEN-1)))
// //     val shamt = io.in_B(4,0).asUInt
// //     val shin = Mux( io.alu_Op(3),io.in_A,Reverse(io.in_A))
// //     val shiftr = (Cat(io.alu_Op (0) && shin (XLEN-1),shin).asSInt >> shamt)(XLEN-1, 0)
// //     val shiftl = Reverse(shiftr)
// //     val out = Mux(io.alu_Op === ALU_ADD || io.alu_Op === ALU_SUB, sum,
// //               Mux((io.alu_Op === ALU_SLT  || io.alu_Op === ALU_SLTU) && (io.in_A < io.in_B ), 1.U,
// //               Mux(io.alu_Op === ALU_SRA  || io.alu_Op === ALU_SRL, shiftr ,
// //               Mux(io.alu_Op === ALU_SLL, shiftl,
// //               Mux(io.alu_Op === ALU_AND, (io.in_A & io.in_B),
// //               Mux(io.alu_Op === ALU_OR , (io.in_A | io.in_B),
// //               Mux(io.alu_Op === ALU_XOR , ( io.in_A ^ io.in_B),
// //               Mux(io.alu_Op === ALU_COPY_A , io.in_A,
// //               Mux(io.alu_Op === ALU_COPY_B , io.in_B, 0.U)))))))))

// //     io.out := out
// //     io.sum := sum

// //     }


package Datapath

import chisel3._
import chisel3.util._

class AluIO(in_out_Width:Int,AluCtrl:Int) extends Bundle{
    val AluControl = Input(UInt(AluCtrl.W))
	val in1 = Input(SInt(in_out_Width.W))
	val in2 = Input(SInt(in_out_Width.W))
	val Branch = Output(Bool())
	val out = Output(SInt(in_out_Width.W))
}

class Alu extends Module with Config {
    val io = IO(new AluIO(WLEN,AluCtrl))
	//Add Addi
	when (io.AluControl === "b00000".U){io.out := io.in1 + io.in2}
	//Sll Slli Sra Srai
	.elsewhen (io.AluControl === "b00001".U){io.out := io.in1 << io.in2(4,0)}
	//Xor Xori
	.elsewhen (io.AluControl === "b00100".U){io.out := io.in1 ^ io.in2}
	//Srl Srli
	.elsewhen (io.AluControl === "b00101".U || io.AluControl === "b01101".U){io.out := io.in1 >> io.in2(4,0)}
	//Or Ori
	.elsewhen (io.AluControl === "b00110".U){io.out := io.in1 | io.in2}
	//And Andi
	.elsewhen (io.AluControl === "b00111".U){io.out := io.in1 & io.in2}
	//Sub
	.elsewhen (io.AluControl === "b01000".U){io.out := io.in1 - io.in2}
	//Jal Jalr
	.elsewhen (io.AluControl === "b11111".U){io.out := io.in1}
	//Bge
	.elsewhen (io.AluControl === "b10101".U){
		when (io.in1 >= io.in2){io.out := 1.S}
		.otherwise {io.out := 0.S}}
	//Bgeu
	.elsewhen (io.AluControl === "b10111".U){
		when (io.in1.asUInt >= io.in2.asUInt){io.out := 1.S}
		.otherwise {io.out := 0.S}}
	//Sltu Sltui Bltu
	.elsewhen (io.AluControl === "b00011".U || io.AluControl === "b10110".U){
		when (io.in1.asUInt < io.in2.asUInt){io.out := 1.S}
		.otherwise {io.out := 0.S}}
	//Beq
	.elsewhen (io.AluControl === "b10000".U){
		when (io.in1 === io.in2){io.out := 1.S}
		.otherwise {io.out := 0.S}}
	//Slt Slti Blt
	.elsewhen (io.AluControl === "b00010".U || io.AluControl === "b10100".U){
		when (io.in1 < io.in2){io.out := 1.S}
		.otherwise {io.out := 0.S}}
	//Bne
	.elsewhen (io.AluControl === "b10001".U){
		when (io.in1 =/= io.in2){io.out := 1.S}
		.otherwise {io.out := 0.S}}
	.otherwise {io.out := DontCare}

	//Branch
	when (io.out === 1.S && io.AluControl(4,3) === "b10".U){io.Branch := "b1".U}
	.otherwise {io.Branch := "b0".U}
}
