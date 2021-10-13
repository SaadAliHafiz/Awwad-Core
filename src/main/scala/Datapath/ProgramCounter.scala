package Datapath

import chisel3._
 
class PC_IO( width : Int ) extends Bundle{

    val in = Input(UInt(width.W))
    val pc = Output(UInt(width.W))
    val pcPlus4 = Output(UInt(width.W))

}

class PC(width_parameter: Int) extends Module {

    val io = IO(new PC_IO(width_parameter))

    val reg = RegInit(0.U(width_parameter.W))
    reg := io.in
    io.pc := reg
    io.pcPlus4 := reg + 4.U
}