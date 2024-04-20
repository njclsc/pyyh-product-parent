package com.zh.collection.business.handler;

import java.net.InetSocketAddress;
import java.util.List;

import com.zh.collection.util.ContainerUtil;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;

public class Show_1_ZHUDPDecoder extends MessageToMessageDecoder<Object>{
	private String head = "02030405";
	private StringBuffer buff = new StringBuffer();
	@Override
	protected void decode(ChannelHandlerContext arg0, Object arg1, List<Object> arg2) throws Exception {
		// TODO Auto-generated method stub
		DatagramPacket dp = (DatagramPacket)arg1;
		InetSocketAddress isa = dp.sender();
		ByteBuf buf = dp.content();
		int len = buf.readableBytes();
		byte[] data = new byte[len];
		buf.readBytes(data);
		String _data = byte2HexString(data);
		System.out.println(len + "   " + _data.length() + "==2==>>>>=====" + _data);
		if(_data.indexOf("02030405") >= 0){
		_data = _data.substring(_data.indexOf("02030405"));
		}
		String[] _datas = _data.split(head);
		for(String tmp : _datas){
			if(tmp == null || tmp.length() < 1){
				continue;
			}
			if(verify(tmp)){
				reponse2Device(arg0, tmp, isa);
				String key = _data.substring(12, 16);
				String addr =  dp.sender().getAddress().getHostAddress() + ":" + dp.sender().getPort();
//				System.out.println("-----1--->>>---" + _data);
//				System.out.println(key + "----->>>>----" + addr);
//				if(!ContainerUtil.getDevAddress().containsKey(key)){
					ContainerUtil.getDevAddress().put(key, addr);
//				}
				arg2.add(tmp);
			}
		}
		
	}
	//回复设备
	private void reponse2Device(ChannelHandlerContext ctx, String reciveData, InetSocketAddress targetAddress){
		String devId = reciveData.substring(4, 8);
		String cmdType = reciveData.substring(8, 10);
		if(cmdType.equals("42")){
			return;
		}
		String sn = reciveData.substring(10, 12);
		buff.append(head);buff.append("000B");buff.append(devId);buff.append(cmdType);
		buff.append(sn);
		String data = buff.toString();
		int tmp = 0;
		for(int i = 0; i < data.length(); i += 2){
			tmp += Integer.parseInt(data.substring(i, i + 2), 16);
		}
		String v1 = Integer.toHexString(((tmp  & 0xF0) >> 4));
		String v2 = Integer.toHexString(tmp & 0xF);
		buff.append(v1);buff.append(v2);
		String data1 = buff.toString();
		byte[] reponse = new byte[data1.length() / 2];
		for(int i = 0; i < reponse.length; i++){
			reponse[i] = (byte)Integer.parseInt(data1.substring(i * 2, (i * 2) + 2), 16);
		}
		buff.delete(0, buff.length());
		ByteBuf rep = Unpooled.copiedBuffer(reponse);
		DatagramPacket dp = new DatagramPacket(rep, targetAddress);
		ctx.writeAndFlush(dp);
	}
	
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
