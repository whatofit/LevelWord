package level;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import model.Sent;
import model.Word;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import util.FileUtil;
import util.Utils;

public class XmlWordVisitor extends VisitorSupport {
	private String mXmlWordFile = "";
	// private Document doc;
	private Word mWord;
	// private List<Sent> sents = new ArrayList<Sent>();
	private Sent sent = null;
	// private String curPos; // 当前词性
	// 用来存放每次遍历后的元素名称(节点名称)
	private String tagName;

	/** 构造函数 */
	public XmlWordVisitor() {
		// System.out.println("--------------WordsVisitor()-------");
	}

	public Document getDocument(String xmlWordFile) {
		// System.out.println("--------------getDocument(xmlWordFile)-------");
		this.mXmlWordFile = xmlWordFile;

		SAXReader saxReader = new SAXReader();
		// this.doc = saxReader.read(file);
		Document document = null;
		try {
			// 实体转换:
			// & ==> &amp;
			// < ==> &lt;
			// > ==> &gt;
			// " ==> &quot;
			// ' ==> apos;

			// aa = aa.replaceAll("mapWidth='\\d*'","mapWidth='1000'");
			String body = FileUtil.readFile(mXmlWordFile);
			String retXml = Utils.replaceAngleBrackets(body);
			// System.out.println("--ret---" + retXml);
			// document = saxReader.read(new File(mXmlWordFile));
			// Document doc = builder.parse();
			document = saxReader.read(new InputSource(new ByteArrayInputStream(
					retXml.getBytes("utf-8"))));
		} catch (DocumentException e) {
			mWord = new Word();
			String[] name2 = Utils.splitFileName(mXmlWordFile);
			mWord.setWordFrequency(name2[0]);
			mWord.setKey(name2[1]);
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return document;
	}

	/**
	 * 对于元素节点，判断是否只包含文本内容，如是，则打印标记的名字和 元素的内容。如果不是，则只打印标记的名字
	 */
	public void visit(Element node) {
		if (node.isTextOnly()) {
			// System.out.println("element : " + node.getName() + " = "
			// + node.getText());
			tagName = node.getName();
			String data = node.getText();
			if (tagName.equals("key")) {
				mWord.setKey(data);
			} else if (tagName.equals("ps")) {
				if (mWord.getPs() == null) {
					mWord.setPs("[" + data + "]");
				} else {
					mWord.setPs2("[" + data + "]");
				}
			} else if (tagName.equals("pron")) {
				if (mWord.getPron() == null) {
					mWord.setPron(data);
				} else {
					mWord.setPron2(data);
				}
				// } else if (tagName.equals("pos")) {
				// curPos = data;
				// } else if (tagName.equals("acceptation")) {
				// mWord.setAcceptation(curPos, data);
			} else if (tagName.equals("pos")) {
				mWord.addPathsOfSpeech(data);
			} else if (tagName.equals("acceptation")) {
				mWord.addMeaning(data);
			} else if (tagName.equals("orig")) {
				sent.setOrig(data);
				// } else if (tagName.equals("pron")) {
				// sent.setPronUrl(data);
			} else if (tagName.equals("trans")) {
				sent.setTrans(data);
				// sents.add(sent);
				mWord.addSent(sent);
				sent = null;
			}
		} else {
			// System.out.println("--------" + node.getName() + "--------");
			String qName = node.getName();
			if (qName.equals("sent")) {
				// System.out.println("-----qName.equals(sent)---");
				sent = new Sent();
			} else if (qName.equals("dict")) {
				// System.out.println("-----qName.equals(dict)---");
				mWord = new Word();
				// System.out.println("--------------visit()--new Word()");
			}
			tagName = qName;
		}
	}

//	public Word getWord() {
//		return mWord;
//	}
	
	public Word getWord(String xmlWordFile) throws Exception {
		getDocument(xmlWordFile).accept(this);
		return mWord;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
