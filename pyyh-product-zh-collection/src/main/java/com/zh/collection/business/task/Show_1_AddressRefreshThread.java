package com.zh.collection.business.task;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;


public class Show_1_AddressRefreshThread extends Thread{
	private DatagramSocket ds;
	private String addr;
	public Show_1_AddressRefreshThread(InetSocketAddress addr) throws SocketException{
		this.ds = new DatagramSocket(addr);
		this.addr = addr.getAddress().getHostAddress() + ":" + addr.getPort();
		System.out.println("--=");
	}
	public void run(){
		while(true){
			DatagramPacket dp = new DatagramPacket(new byte[128], 128);
			try {
				ds.receive(dp);
				int len = dp.getLength();
				byte[] rd = dp.getData();
				byte[] data = new byte[len];
				for(int i = 0; i < len; i++){
					data[i] = rd[i];
				}
				StringBuffer sb = new StringBuffer();
				for(byte b : data){
					sb.append((char)b);
				}
//				String _data = byte2HexString(data);
				String _data = sb.toString();
				if(verify(_data.substring(8))){
					String d1 = _data.substring(22, 36);
					String d2 = _data.substring(36, 50);
					System.out.println(dp.getSocketAddress() + "-->> "+ d1 + "---" + d2);
					System.out.println(dp.getSocketAddress() + "-->> "+ d1 + "---" + d2);
				}
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
