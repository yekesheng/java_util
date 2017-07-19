import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class GameBase64 {

	public static Charset UTF8_CHARSET = Charset.forName("UTF-8");
	public static byte[] charArray = "Q8vN-ryaEJGoTWOtK_qMkh5RZ6LxcUA3dnzeHu2XjSbVsFYwfPD94C0lm1Ip7gBi".getBytes(UTF8_CHARSET);
	
	public static int hashIndex[] = {
			0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF,
			0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF,
			0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF,
			0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF,
			0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF,
			0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF,
			0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF,
			0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF,
			0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, // 45
			4, 0xFFFFFFFF, 0xFFFFFFFF, 0x36, 0x39, 0x26, 0x1F,          // 52
			0x34, 0x16, 0x19, 0x3C, 1, 0x33, 0xFFFFFFFF, 0xFFFFFFFF,    // 60
			0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, // 65
			0x1E, 0x3E, 0x35, 0x32, 8, 0x2D, 0xA, 0x24, 0x3A, 9,        // 75
			0x10, 0x1A, 0x13, 3, 0xE, 0x31, 0, 0x17, 0x29, 0xC,
			0x1D, 0x2B, 0xD, 0x27, 0x2E, 0x18, 0xFFFFFFFF, 0xFFFFFFFF,
			0xFFFFFFFF, 0xFFFFFFFF, 0x11, 0xFFFFFFFF, 7, 0x2A,
			0x1C, 0x20, 0x23, 0x30, 0x3D, 0x15, 0x3F, 0x28, 0x14,
			0x37, 0x38, 0x21, 0xB, 0x3B, 0x12, 5, 0x2C, 0xF, 0x25,
			2, 0x2F, 0x1B, 6, 0x22, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF,
			0xFFFFFFFF, 0xFFFFFFFF, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,	
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,	
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,	
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,	
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,	
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,	
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
		};

	public static String decode(String inStr) throws UnsupportedEncodingException {
		byte[] in = inStr.getBytes(UTF8_CHARSET);
		byte[] decBytes = new byte[in.length];
		
		int i = 0;
		int j = 0;
		int inLen = in.length;

		do
		{
			if ((inLen - i) <= 3) {
				break;
			}
			int iTmp1 = (hashIndex[in[i+1]] >> 4) & 3 & 0xFF;
			int iTmp2 = 4 * (hashIndex[in[i+0]] & 0xFF);
			decBytes[j+0] = (byte) (iTmp1 | iTmp2);
			decBytes[j+1] = (byte) ((hashIndex[in[i+2]] >> 2) & 0xF | 16 * (hashIndex[in[i+1]] & 0xFF));
			int iIdx = in[i+2] & 0xFF;
			int jIdx = in[i+3] & 0xFF;
			decBytes[j+2] = (byte) (((hashIndex[iIdx] & 0xFF) << 6) | (hashIndex[jIdx] & 0xFF));
			j += 3;
			i += 4;
		} while (true);

		if ((inLen - i) == 3)
		{
			decBytes[j+0] = (byte) ((hashIndex[in[i+1]] >> 4) & 3 | 4 * (hashIndex[in[i+0]] & 0xFF));
			decBytes[j+1] = (byte) ((hashIndex[in[i+2]] << 26 >> 28) | 16 * (hashIndex[in[i+1]] & 0xFF));
			decBytes[j+2] = (byte) ((hashIndex[in[i+2]] & 0xFF) << 6);
			j += 3;
		}
		else if ((inLen - i) == 2)
		{
			decBytes[j+0] = (byte) ((hashIndex[in[i+1]] << 26 >> 30) | 4 * (hashIndex[in[i+0]] & 0xFF));
			decBytes[j+1] = (byte) (16 * (hashIndex[in[i+1]] & 0xFF));
			j += 2;
		}
		else if ((inLen - i) == 1)
		{
			decBytes[j+0] = (byte) (4 * (hashIndex[in[i+0]] & 0xFF));
			j += 1;
		}
		if (j < 1) {
			return new String("");
		}
		
		return new String(decBytes, 0, j - 1, UTF8_CHARSET);
	}

	public static String encode(String inStr) throws UnsupportedEncodingException {
		byte[] in = inStr.getBytes(UTF8_CHARSET);
		System.out.println("raw len:" + in.length);
		byte[] encBytes = new byte[in.length * 2 + 4];
		
		int i = 0;
		int j = 0;
		int inLen = in.length;

		do
		{
			if ((inLen - i) <= 2) {
				break;
			}
			int b0 = 0x00FF & in[i+0];
			int b1 = 0x00FF & in[i+1];
			int b2 = 0x00FF & in[i+2];
			int idx = b0 >> 2;
			encBytes[j+0] = charArray[idx & 0x3F];
			idx = (b1 >> 4) | ((b0 << 4) & 0x30);
			encBytes[j+1] = charArray[idx & 0x3F];
			idx = (b2 >> 6) | ((b1 << 2) & 0x3C);
			encBytes[j+2] = charArray[idx & 0x3F];
			encBytes[j+3] = charArray[b2 & 0x3F];
			i += 3;
			j += 4;
		} while (true);

		if ((inLen - i) > 0)
		{
			int b0 = 0x00FF & in[i+0];
			encBytes[j+0] = charArray[b0 >> 2];
			if ((inLen - i) == 2)
			{
				int b1 = 0x00FF & in[i+1];
				encBytes[j+1] = charArray[(b0 << 4) & 0x30 | (b1 >> 4)];
				encBytes[j+2] = charArray[(b1 << 2) & 0x3C];
				j += 3;
			}
			else
			{
				encBytes[j+1] = charArray[(b0 << 4) & 0x30];
				j += 2;
			}
		}
		if (j < 1) {
			return new String("");
		}

		System.out.println("enc len:" + j);
		return new String(encBytes, 0, j, UTF8_CHARSET);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String testStr = "ZR8fh2hDc0uwxH1nx5kgTzm9oe-2xXKgh4uyqq6Fx0_uxNCO6RnCcDsCJX8n60h3L51H6RdgTq6fLRnuxN49oeQ2L5CuLM49WMHPTekfWMT9OM-CWMk2xlJS60uYtMEfJX_1cykgxyu9Uv69ARW8Z0WwU514tMQ2ZRZgTMH2cyr4Z0n3clhftM-2ZlTgTv6n6aJ56RJOZ5CutMKYWvm4JXkgWNkfTMQfWMT4WM_HT9-9WeKlTNcfTyKDTNheZeTPTNQ2ZR8fcyFXM2rF6MCex04YU2u0xD1XZ5CuJ2rfcr6ucXWSx0mgTMTPJ2hsZR8965_4L5CutMd4TMH1TNH9JX8SZC_1cykgU0hzcv69tMEuW4T0TNQ1TNECJ2S0chg4AR8utMQ";
		try {
			String decStr = decode(testStr);
			System.out.println(decStr);
			String encStr;
			encStr = encode(decStr);
			System.out.println(encStr);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
