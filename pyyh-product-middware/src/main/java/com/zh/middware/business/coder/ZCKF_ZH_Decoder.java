package com.zh.middware.business.coder;

import java.net.InetSocketAddress;
import java.util.List;

import com.zh.middware.util.ContainerUtil;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;

public class ZCKF_ZH_Decoder extends MessageToMessageDecoder<Object>{
	private String head = "02030405";
	private StringBuffer buff = new StringBuffer();
	@Override
	protected void decode(ChannelHandlerContext arg0, Object arg1, List<Object> arg2) throws Exception {
		// TODO Auto-generated method stub
		DatagramPacket dp = (DatagramPacket)arg1;
		InetSocketAddress isa = dp.sender();
		ByteBuf buf = dp.content();
		byte[] bytes = new byte[buf.readableBytes()];
		buf.readBytes(bytes);
		String data = byte2HexString(bytes);
//		System.out.println("原始数据--> " + data);
		for(String s : data.split(head)){
			if(s == null || s.length() < 1){
				continue;
			}
			if(verify(s)){
				String devId = s.substring(4, 8);
				String addr = isa.getAddress().getHostAddress() + ":" + isa.getPort();
				ContainerUtil.getDevAddr().put(devId, addr);
				arg2.add(s);
			}
		}
	}
//	private void reponse2Device(ChannelHandlerContext ctx, String reciveData, InetSocketAddress targetAddress){
//		String devId = reciveData.substring(4, 8);
//		String cmdType = reciveData.substring(8, 10);
//		if(cmdType.equals("42")){
//			return;
//		}
//		String sn = reciveData.substring(10, 12);
//		buff.append(head);buff.append("000B");buff.append(devId);buff.append(cmdType);
//		buff.append(sn);
//		String data = buff.toString();
//		int tmp = 0;
//		for(int i = 0; i < data.length(); i += 2){
//			tmp += Integer.parseInt(data.substring(i, i + 2), 16);
//		}
//		String v1 = Integer.toHexString(((tmp  & 0xF0) >> 4));
//		String v2 = Integer.toHexString(tmp & 0xF);
//		buff.append(v1);buff.append(v2);
//		String data1 = buff.toString();
//		byte[] reponse = new byte[data1.length() / 2];
//		for(int i = 0; i < reponse.length; i++){
//			reponse[i] = (byte)Integer.parseInt(data1.substring(i * 2, (i * 2) + 2), 16);
//		}
//		buff.delete(0, buff.length());
//		ByteBuf rep = Unpooled.copiedBuffer(reponse);
//		DatagramPacket dp = new DatagramPacket(rep, targetAddress);
//		ctx.writeAndFlush(dp);
//	}
	private String byte2HexString(byte[] bits){
		StringBuffer sb = new StringBuffer();
		for(byte bit : bits){
			sb.append(Integer.toHexString((bit & 0xF0) >> 4));
			sb.append(Integer.toHexString(bit & 0xF));
		}
		return sb.toString().toUpperCase();
	}
	private boolean verify(String str){
		int len = str.length();
		int count = 0x0E;
		for(int i = 0; i < len - 2; i += 2){
			count += Integer.parseInt(str.substring(i, i + 2), 16);
		}
		if(Integer.parseInt(str.substring(len - 2, len), 16) == (count & 0xFF)){
			return true;
		}else{
			return false;
		}
	}
}
