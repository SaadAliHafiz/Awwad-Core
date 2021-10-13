package Datapath

import chisel3._
import chisel3.util._

class Jalr_IO[T <: Data]( width : T ) extends Bundle{

    val rs1 = Input(width)
	val Type = Input(width)
    val out = Output(width)
}

class Jalr(size:UInt) extends Module{

	val io = IO(new Jalr_IO(size))
	
    io.out :=  (io.rs1 + io.Type) & 4294967294L.U
}