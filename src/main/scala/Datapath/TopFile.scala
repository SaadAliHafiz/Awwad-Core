// package Datapath

// import chisel3._
// import chisel3.util._

// class TopIO extends Bundle{
//     val Regout = Output(SInt(32.W))
// }

// class Top extends Module {
    
//     val io = IO (new TopIO) 
	
// 	val alu = Module(new Alu)
//     val aluControl = Module(new AluControl(3))
// 	val control = Module(new Control)
// 	val dataMemory = Module(new DataMem(new Parameters(32,32)))
// 	val ImmediateGeneration = Module(new ImmGen(UInt(32.W),SInt(32.W)))
// 	val Memory = Module(new InstructionMemory)
// 	val jalr = Module(new Jalr(UInt(32.W)))
// 	val pc = Module(new PC(32))
// 	val registerFile = Module(new RegFile)

//     // Instruction memory connections

// 	Memory.io.addr := pc.io.pc(11,2)
// 	pc.io.in := pc.io.pc4

//     //  Control

//     control.io.opcode := Memory.io.inst(6,0)
// 	ImmediateGeneration.io.instr := Memory.io.inst
// 	ImmediateGeneration.io.pc := pc.io.pc

//     // Reg File

//     registerFile.io.regEn := Control.io.regEn
// 	registerFile.io.raddr1 := Memory.io.inst(19,15)
// 	registerFile.io.raddr2 := Memory.io.inst(24,20)
// 	registerFile.io.waddr := Memory.io.inst(11,7)

//     // Alu op

// 	aluControl.io.aluOp := Control.io.AluOp
// 	aluControl.io.funct3 := Memory.io.rdData(14,12)
// 	aluControl.io.funct7 := Memory.io.rdData(30)

// 	//Control Decode

// 	//Alu.io.in1 := Register.io.rs1
// 	when (Control.io.immSel === "b00".U && Control.io.opB_Sel === "b1".U){alu.io.in_B := ImmediateGeneration.io.immd_se}
// 	.elsewhen (Control.io.immSel === "b01".U && Control.io.opB_Sel === "b1".U){alu.io.in_B := ImmediateGeneration.io.immd_se}
// 	.elsewhen (Control.io.i	mmSel === "b10".U && Control.io.opB_Sel === "b1".U){
// 		alu.io.in_B := ImmediateGeneration.io.immd_se}
// 		//Alu.io.in1:= Register.io.rs1
// 		//Pc.io.input:=Pc.io.pc4}
// 	.otherwise {alu.io.in_B := registerFile.io.rs2}
// 	alu.io.aluControl := aluControl.io.control
// 	registerFile.io.wdata := alu.io.out
// 	io.Regout := registerFile.io.wdata

// }