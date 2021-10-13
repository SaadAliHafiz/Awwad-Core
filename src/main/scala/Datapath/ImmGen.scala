package Datapath

import chisel3._
import chisel3.util._

class Imm_Gen_IO[T <: Data](uInt: T, sInt: T) extends Bundle {
	val instr = Input(uInt)
	val immd_se = Output(sInt)
    val pc = Input(uInt)
}

class ImmGen(sizeUint:UInt,sizeSint:SInt) extends Module {

	val io = IO(new Imm_Gen_IO(sizeUint:UInt, sizeSint:SInt))
	
	io.immd_se := 0.S

	when(io.instr(6,0)==="b0100011".U){                 //S_Immediate
		io.immd_se := (Cat(Fill(20,io.instr(31)),io.instr(31,25),io.instr(11,7))).asSInt
	}
    
    .elsewhen(io.instr(6,0)==="b0010111".U){              //U_Immediate
		io.immd_se := (Cat(io.instr(31),io.instr(30,25),io.instr(24,21),io.instr(20),io.instr(19,12)) << 12.U).asSInt
	}
    
    .elsewhen(io.instr(6,0)==="b0010011".U){            //I_Immediate
		io.immd_se := (Cat(Fill(20,io.instr(31)),io.instr(31,20))).asSInt
	}
    
    .elsewhen(io.instr(6,0)==="b1100011".U){           //SB_Immediate
		io.immd_se := (Cat(Fill(19,io.instr(31)),io.instr(31),io.instr(7),io.instr(30,25),io.instr(11,8), 0.U)+io.pc.asUInt).asSInt
	}
    
    .elsewhen(io.instr(6,0)==="b1101111".U){      //UJ_Immediate
		io.immd_se := (Cat(Fill(11,io.instr(31)),io.instr(31),io.instr(19,12),io.instr(20),io.instr(30,25),io.instr(24,21), 0.U)).asSInt	}
}