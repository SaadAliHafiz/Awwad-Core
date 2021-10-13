package Datapath

import chisel3._
import chisel3.util._

trait Config {

// Reg File

    val REGFILE_LEN = 32
    val XLEN = 32
    val address = UInt(5.W)
    val data = SInt(32.W)


    val WLEN = 32
    val ALUOP_SIG_LEN = 4
    val INST_MEM_LEN = 32
    val func3 = UInt(3.W)

// Instr Mem

    val datatype = UInt
    val AWLEN = 10
    val nRows = math.pow(2,AWLEN).toInt
    val initFile = "src/main/scala/RV32ISingleCycleDatapath/Datapath/instrFile.txt"
    val opCodeWidth=UInt(7.W)

    //ALU
    
    val in_out_Width=32
    val AluCtrl=5

}