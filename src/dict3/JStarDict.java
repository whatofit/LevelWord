//package dict3;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.omg.IOP.Encoding;
//
//
//public class JStarDict
//{
//   private String idxFile;
//   private String dictFile;
//
//   public JStarDict(String idx, String dict)
//   {
//       this.idxFile = idx;
//       this.dictFile = dict;
//   }
//
//   public List<String> Words(String key)
//   {
//       List<String> words = new ArrayList<String>();
////       FileStream fsIdx = new FileStream(this.idxFile, FileMode.Open, FileAccess.Read);
////       BinaryReader brIdx = new BinaryReader(fsIdx, Encoding.UTF8);
//       File file = new File(this.idxFile);
//       InputStream fisIdx = null;//Reader fisIdx = null;
//       // 一次读一个字节
//       fisIdx = new FileInputStream(file);
//       byte[] ch = new byte[4096];
//       byte end = (byte)'\0';
//       int index = 0;
//       for (index = 0; index < 4096; index++) {
//           ch[index] = end;	//全部重置为0
//       }
//       index = 0;
//       int tempchar;
//       while ((tempchar = reader.read()) != -1) {
//        // 对于windows下，\r\n这两个字符在一起时，表示一个换行。
//        // 但如果这两个字符分开显示时，会换两次行。
//        // 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。
//        if (((char) tempchar) != '\r') {
//         System.out.print((char) tempchar);
//        }
//       }
//       
//       while (true)
//       {
//           try
//           {
//               ch[index] = fisIdx.read();
//               index++;
//           }
//           catch (EndOfStreamException)
//           {
//               break;
//           }
//           if (ch[index - 1] == end)
//           {
//               byte[] word = new byte[index - 1];
//               for (int i = 0; i < index - 1; i++)
//               {
//                   word[i] = ch[i];
//                   ch[i] = 0;
//               }
//               String queryString = Encoding.UTF8.GetString(word);
//             if (queryString.StartsWith(key, true, System.Globalization.CultureInfo.CurrentCulture))
//             {
//                 words.add(queryString);
//             }
//           }
//       }
//       brIdx.Close();
//       fsIdx.Close();
//       return words;
//   }
//
//   public String Result(String originalText)
//   {
//	   String translatedText = String.Empty;
//       FileStream fsIdx = new FileStream(this.idxFile, FileMode.Open, FileAccess.Read);
//       BinaryReader brIdx = new BinaryReader(fsIdx, Encoding.UTF8);
//       FileStream fsDict = new FileStream(this.dictFile, FileMode.Open, FileAccess.Read);
//       BinaryReader brDict = new BinaryReader(fsDict, Encoding.UTF8);
//       byte[] ch = new byte[4096];
//       byte end = (byte)'\0';
//       int pos, size;
//       int index = 0;
//       for (index = 0; index < 4096; index++)
//           ch[index] = (byte)'\0';
//       index = 0;
//       while (true)
//       {
//           try
//           {
//               ch[index] = brIdx.ReadByte();
//               index++;
//           }
//           catch (EndOfStreamException)
//           {
//               break;
//           }
//           if (ch[index - 1] == end)
//           {
//               byte[] word = new byte[index - 1];
//               for (int i = 0; i < index - 1; i++)
//               {
//                   word[i] = ch[i];
//                   ch[i] = 0;
//               }
//               String queryString = Encoding.UTF8.GetString(word);
//
//               index = 0;
//               byte[] tmp = brIdx.ReadBytes(4);
//               pos = tmp[3] + (tmp[2] << 8) + (tmp[1] << 16) + (tmp[0] << 24); 
//               tmp = brIdx.ReadBytes(4);
//               size = tmp[3] + (tmp[2] << 8) + (tmp[1] << 16) + (tmp[0] << 24); 
//               byte[] content = brDict.ReadBytes(size);
//               String outText = Encoding.UTF8.GetString(content).Replace(" ", "</br>") + "</br>";
//               //是查询的单词
//               if (queryString == originalText)
//               {
//                   translatedText = outText;
//                   break;
//               }
//           }
//       }
//       brDict.Close();
//       fsDict.Close();
//       brIdx.Close();
//       fsIdx.Close();
//       return translatedText;
//   }
//
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//
//	}
// }