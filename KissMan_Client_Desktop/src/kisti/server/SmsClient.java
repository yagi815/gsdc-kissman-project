package kisti.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedInputStream;
import java.util.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import java.nio.*;
import org.apache.commons.lang.StringUtils;

public class SmsClient {

	private InetAddress addr = null;
	private String host = null;
	private String port = null;
	// private String host = "127.0.0.1";
	// private String port = "8999";

	private String szCAller = null; // �߽Ź�ȣ(��ȸ�� ���� 070��ȣ)
	private String szCallback = null; // ȸ�� ��ȣ
	private String szCallee = null; // ���� ��ȣ
	private String szSmsData = null; // sms ����
	private String seq = null;
	int Sendian = 0; // ������ ����� 0:��Ʋ ����� 1:�򿣵��
	int Rendian = 0; // �޴� ����� 0:��Ʋ ����� 1:�򿣵��

	public SmsClient() {

	}

	public HashMap send(String szCAller, String szCallback, String szCallee,
			String szSmsData, String seq, int Sendian, int Rendian)
			throws IOException {
		// Socket Creation
		Socket socket = null;
		BufferedOutputStream bout = null;
		BufferedInputStream bin = null;
		HashMap result = null;

		this.host = "127.0.0.1";
		this.port = "10001";
		this.szCAller = szCAller; // �߽Ź�ȣ(��ȸ�� ���� 070��ȣ)
		this.szCallback = szCallback; // ȸ�� ��ȣ
		this.szCallee = szCallee; // ���� ��ȣ
		this.szSmsData = szSmsData; // sms ����
		this.seq = seq; // ����
		this.Sendian = Sendian; // ������ ����� 0:��Ʋ ����� 1:�򿣵��
		this.Rendian = Rendian; // �޴� ����� 0:��Ʋ ����� 1:�򿣵��

		try {
			System.out.println("host" + this.host);
			addr = InetAddress.getByName(this.host);

			socket = new Socket(this.addr, Integer.parseInt(this.port));
			socket.setSoTimeout(10000); // Timeout : 10 secs.

			System.out.println("socket = " + socket);

			// ���� ���� ��Ʈ��
			bout = new BufferedOutputStream(socket.getOutputStream());

			bin = new BufferedInputStream(socket.getInputStream());

			ByteBuffer buffer = ByteBuffer.allocate(220); // 220����Ʈ ���� ����
			if (Sendian == 0) {
				buffer.order(ByteOrder.LITTLE_ENDIAN);
			} // c���� little_endian������� �����Ѵ�. �ڹٴ� big_endian��Ļ��
			else {
				buffer.order(ByteOrder.BIG_ENDIAN);
			}

			System.out.println("Byte order : " + buffer.order()); // ����� ��� ������

			buffer.putShort((short) 0xfefe); // 2����Ʈ -�⺻ �� ������ ����

			buffer.putShort((short) 220); // 2����Ʈ -�� ����(�ٵ�+���)

			buffer.putInt((int) 0x0000); // 4����Ʈ -0x0000 :��û -ox0001:����

			buffer.putInt(Integer.parseInt(this.seq)); // 4����Ʈ -����

			buffer.putInt((int) 0x0100); // 4����Ʈ -�⺻��

			String ret = ""; // �����ϴ� ������ ������� 40����Ʈ�� �ɶ����� null�� ä��

			// �߽Ź�ȣ(��ȸ�� ���� 070��ȣ)
			if (this.szCAller.getBytes().length > 40) {
				System.out.println("alert !!! szCAller over 40!!!");
			}
			ret = StringUtils.rightPad(this.szCAller, 40, (char) 0x00); // �������
																		// ä��
			buffer.put(ret.getBytes()); // szCAller[40] //����Ʈ ������ �߽Ź�ȣ�� ����

			// ȸ�� ��ȣ
			if (this.szCallback.getBytes().length > 40) {
				System.out.println("alert !!! szCallback over 40!!!");
			}
			ret = StringUtils.rightPad(this.szCallback, 40, (char) 0x00); // �������
																			// ä��
			buffer.put(ret.getBytes()); // szCallback[40] //����Ʈ ������ ȸ�� ��ȣ�� ����

			// ���� ��ȣ
			if (this.szCallee.getBytes().length > 40) {
				System.out.println("alert !!! szCallee over 40!!!");
			}
			ret = StringUtils.rightPad(this.szCallee, 40, (char) 0x00); // �������
																		// ä��
			buffer.put(ret.getBytes()); // szCallee[40] //���� ��ȣ

			// sms ����
			if (this.szSmsData.getBytes("euc-kr").length > 80) {
				System.out.println("alert !!! szSmsData over 80!!! cut!!!!");
			}

			byte[] cotent = new byte[84]; // 84����Ʈ ���������� �����δ� 80����Ʈ�� ���
			System.arraycopy(this.szSmsData.getBytes("euc-kr"), 0, cotent, 0,
					this.szSmsData.getBytes("euc-kr").length); // sms������ 84����Ʈ
																// �迭�� ����
			buffer.put(cotent); // szSmsData[84] //����Ʈ ������ ������ �迭�� ����

			buffer.flip();

			byte[] results = buffer.array(); // ���ۿ� ����ִ� ������ ����Ʈ �迭�� ���� �ִ´�

			System.out.println("send packet--->" + new String(results));
			System.out.println("send size--->" + results.length);

			bout.write(results); // �Ž�send ��Ʈ���� ����Ʈ�迭 �������� ������ ������
			bout.flush(); // ���� ������ ����

			byte[] t = new byte[20]; // 20����Ʈ ����
			bin.read(t, 0, 20); // ���� ��Ʈ������ ���� 20����Ʈ�� �Ѳ����� �д´�

			ByteBuffer buffer2 = ByteBuffer.allocate(20);

			if (Rendian == 0) {
				buffer2.order(ByteOrder.LITTLE_ENDIAN);
			} // c���� little_endian������� �����Ѵ�. �ڹٴ� big_endian��Ļ��
			else {
				buffer2.order(ByteOrder.BIG_ENDIAN);
			}

			byte[] t_sub1 = getbytes(t, 8, 4);
			byte[] t_sub2 = getbytes(t, 16, 4);

			buffer2.put(t_sub1); // ���������� ���ۿ� ����

			buffer2.put(t_sub2); // ���۰������ ���ۿ� ����

			int i1 = buffer2.getInt(0); // ���������� ��Ʈ������ ����
			int i2 = buffer2.getInt(4); // ���۰������ ��Ʈ������ ����

			System.out.println("recive seq: " + i1); // ���� ������
			System.out.println("recive result: " + i2); // ���� ���۰����

			result = new HashMap();
			result.put("seq", i1 + "");
			result.put("result", i2 + "");

		} catch (SocketException e1) {
			System.out.println("SOCKET EXCEPTION : " + e1.toString());
		} catch (UnknownHostException e) {
			System.out.println("Server Address Error");
		} catch (Exception e2) {
			System.out.println("EXCEPTION :" + e2.toString());
		} finally {

			if (bout != null) {
				try {
					bout.close();
				} catch (Exception e3) {
				}
			}

			if (bin != null) {
				try {
					bin.close();
				} catch (Exception e4) {
				}
			}

			System.out.println("Socket Closing....");
			socket.close();
		}

		return result;

	}

	public static final byte[] getbytes(byte src[], int offset, int length) {
		byte dest[] = new byte[length];
		System.arraycopy(src, offset, dest, 0, length);
		return dest;
	}

}
