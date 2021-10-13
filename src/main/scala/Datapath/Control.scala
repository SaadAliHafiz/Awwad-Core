// package Datapath
// import chisel3._

// class Control extends Module{
// 	val io = IO(new Bundle{
// 		val opCode = Input(UInt(7.W))
// 		val MemWrite = Output(Bool())
// 		val Branch = Output(Bool())
// 		val MemRead = Output(Bool())
// 		val RegWrite = Output(Bool())
// 		val MemToReg = Output(Bool())
// 		val AluOp = Output(UInt(3.W))
// 		val Operand_aSel = Output(UInt(2.W))
// 		val Operand_bSel = Output(Bool())
// 		val ExtendSel = Output(UInt(2.W))
// 		val NextPcSel = Output(UInt(2.W))
// 	})
// 		val ID = Module(new TypeDecode())
// 		val CD = Module(new CD())
// 		ID.io.opCode := io.opCode
// 		Seq(CD.io.RType,CD.io.Load,CD.io.Store,CD.io.SBType,CD.io.IType,CD.io.Jalr,CD.io.Jal,CD.io.Lui) zip Seq(ID.io.RType,ID.io.Load,ID.io.Store,ID.io.Branch,ID.io.IType,ID.io.Jalr,ID.io.Jal,ID.io.Lui) map{x=> x._1:=x._2}
// 		Seq(io.MemWrite,io.Branch,io.MemRead,io.RegWrite,io.MemToReg,io.AluOp,io.Operand_aSel,io.Operand_bSel,io.ExtendSel,io.NextPcSel) zip Seq(CD.io.MemWrite,CD.io.Branch,CD.io.MemRead, CD.io.RegWrite,CD.io.MemToReg,CD.io.AluOp,CD.io.Operand_aSel,CD.io.Operand_bSel,CD.io.ExtendSel,CD.io.NextPcSel) map{x=> x._1:=x._2}
// }