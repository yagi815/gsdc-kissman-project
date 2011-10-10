package testCode;
public class testWriteCode {

	public testWriteCode() {
		// TODO Auto-generated constructor stub
	}

	public static boolean check_pool() {
		Socket socket = null;
		BufferedReader in = null;
		DataOutputStream out = null;

		String cmdLine = "condor_status";

		try {
			socket = new Socket(Config.CONDOR_IPADDR, Config.PORTNUM);

			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			out = new DataOutputStream(socket.getOutputStream());
			out.flush();
			/* make an integer to unsigned int */
			int userInput = 16;
			userInput <<= 8;
			userInput |= 1;
			userInput &= 0x7FFFFFFF;

			/*
			 * easy and simple way is using writeInt() function, but writing an
			 * integer to socket through writeInt() causes "connection reset"
			 * problem because an additional data being transmitted.
			 * 
			 * In order to resolve this problem, we use write() function after
			 * converting the integer to byte[].
			 */
			String s = Integer.toString(userInput);
			byte[] b = s.getBytes();

			out.write(b, 0, b.length);
			out.write(cmdLine.getBytes(), 0, cmdLine.getBytes().length);

			char[] cbuf = new char[1024];

			while (in.read(cbuf, 0, 1024) != -1) {
				String str = new String(cbuf);
				str = str.trim();

				if (str.contains("Total") && !str.contains("Owner")) {
					PoolStatus.extractInfo(str);
					PoolStatus.printQStatus();
				}

				for (int i = 0; i < 1024; i++)
					cbuf[i] = '\0';
			}
		} catch (UnknownHostException e) {
			PrintMsg.print(DMsgType.ERROR, e.getMessage());
			closeStream(in, out, socket);

			return false;
		} catch (IOException e) {
			PrintMsg.print(DMsgType.ERROR, e.getMessage());
			closeStream(in, out, socket);

			return false;
		}

		closeStream(in, out, socket);

		return true;
	}

	public static void main(String[] args) {

	}

}