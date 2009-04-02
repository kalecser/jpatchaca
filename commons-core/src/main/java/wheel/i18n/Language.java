package wheel.i18n;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

//Refactor: Break this into several classes.
public class Language {
	
	private static final String TRANSLATION_DIRECTORY = "translations";
	
	private static final String MSGSTR = "msgstr \"";

	private static final String MSGID = "msgid \"";

	private static final String MARK_START = "translate("; //This must be changed when the method is refatored.

	private static final String MARK_END = ")";

	private static final String TRANSLATION_FILENAME = "Translation";

	private static final Language instance = new Language();

	public Hashtable<String, String> translationMap = new Hashtable<String, String>();
	
	private String _currentLanguage = "";

	private Language() {
			loadTranslationTemplate();
	}

	public static void load(String language_country) {
		try {
			instance._currentLanguage = language_country;
			instance.loadTranslation();
		} catch (final Exception ioe) {
			System.err.println("Could not find Translation file for " + language_country + " . Please generate it. Sneer still works normally without it.");
		}
	}
	
	public static String current(){
		return instance._currentLanguage;
	}

	public static String translate(String key) { // important! change the MARK_START constant when the method is refatored.
		String result = instance.translationMap.get(key);
		if ((result == null) || (result.isEmpty()))
			result = key;
		return result;
	}

	public static String translate(String key, Object... args) {
		return String.format(translate(key), args);
	}
	
	public static void reset(){
		instance.loadTranslationTemplate();
		instance._currentLanguage = "";
	}

	public void loadTranslationTemplate() {
		try {
			final InputStream stream = this.getClass().getResourceAsStream("/" + TRANSLATION_DIRECTORY + "/" + TRANSLATION_FILENAME + ".pot");
			final BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			parseTranslation(reader);
		} catch (final Exception anything) {
			System.err.println("Could not find Translation file. Please generate it. Sneer still works normally without it.");
		}
	}

	private void parseTranslation(BufferedReader reader) throws IOException {
		String line = "";
		while ((line = reader.readLine()) != null) {
			if (line.startsWith(MSGID)) {
				String msgid = line.substring(MSGID.length(), line.length() - 1);
				final String nextLine = reader.readLine();
				String msgstr = nextLine.substring(MSGSTR.length(), nextLine.length() - 1);
				msgid = msgid.replaceAll("\\\\n", "\n"); //Fix: should check for other types of scape sequences
				msgid = msgid.replaceAll("\\\\\\\"", "\\\"");
				msgstr = msgstr.replaceAll("\\\\n", "\n");
				msgstr = msgstr.replaceAll("\\\\\\\"", "\\\"");
				translationMap.put(msgid, msgstr);
			}
		}
	}

	private void loadTranslation() throws IOException {
		final InputStream stream = getClass().getClassLoader().getResourceAsStream( TRANSLATION_DIRECTORY + "/" + TRANSLATION_FILENAME + "_" + _currentLanguage + ".po");
		final BufferedReader reader = new BufferedReader(new InputStreamReader(stream,Charset.forName("UTF-8")));
		parseTranslation(reader);
	}

	// Fix: the code below is responsible for translation file creation... *maybe* should be moved to an utility class

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	private static void createAndShowGUI() {
		final JFrame frame = new JFrame("Language");
		frame.setResizable(false);
		final Container content = frame.getContentPane();
		content.setBackground(Color.white);
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
		final JButton button1 = new JButton(" Create Template File (.pot)");
		button1.setAlignmentX(Component.CENTER_ALIGNMENT);
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createTemplateFile();
			}
		});
		final JButton button2 = new JButton(" Create New Language file (.po)");
		button2.setAlignmentX(Component.CENTER_ALIGNMENT);
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createLanguageFile();
			}
		});
		final JButton button3 = new JButton(" Create Merged Language file (.po_merge)");
		button3.setAlignmentX(Component.CENTER_ALIGNMENT);
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				merge();
			}
		});

		content.add(button1);
		content.add(button2);
		content.add(button3);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	private static void createLanguageFile() { //Refactor: low priority. lots of redundant code in this class... unify...
		final JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setDialogTitle("Choose the Sources Directory");
		final int returnVal = chooser.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			final File dirFile = chooser.getSelectedFile();
			final String language_country = JOptionPane.showInputDialog("What language? (Examples: pt_BR, fr_FR )");
			InputStream streamIn;
			OutputStream streamOut;
			try {
				streamIn = new FileInputStream(dirFile.getAbsolutePath() + File.separator + TRANSLATION_DIRECTORY + File.separator + TRANSLATION_FILENAME + ".pot");
				streamOut = new FileOutputStream(dirFile.getAbsolutePath() + File.separator + TRANSLATION_DIRECTORY + File.separator + TRANSLATION_FILENAME + "_" + language_country + ".po");
				final BufferedReader reader = new BufferedReader(new InputStreamReader(streamIn,Charset.forName("UTF-8")));
				final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(streamOut,"UTF-8"));
				String line = "";
				while ((line = reader.readLine()) != null) {
					writer.write(line + "\r\n");
				}
				writer.close();
				reader.close();
				JOptionPane.showMessageDialog(null, "Done!");
			} catch (final FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, "Could not find Translation.pot file. Do you generated it?");
			} catch (final IOException unexpected) {
				JOptionPane.showMessageDialog(null, "Unexpected IO problem?");
			}
		}
	}

	private static void createTemplateFile() { //Refactor: low priority. lots of redundant code in this class... unify...
		final JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setDialogTitle("Choose the Sources Directory");
		final int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			final File dirFile = chooser.getSelectedFile();
			extractFromDirectory(dirFile);
			JOptionPane.showMessageDialog(null, "Done!");
		}
	}

	private static void extractFromDirectory(File dirFile) {
		final File targetFile = new File(dirFile.getPath() + File.separator + TRANSLATION_DIRECTORY + File.separator + "Translation.pot");
		generateTranslation(dirFile, targetFile);
	}

	private static void generateTranslation(File dirFile, File targetFile) {
		final List<String> generatedMsgids = new Vector<String>();
		final List<File> files = new Vector<File>();
		listJavaFiles(files, dirFile);
		try {
			final BufferedWriter writer = new BufferedWriter(new FileWriter(targetFile));
			for (final File currentFile : files) {
				if (!currentFile.getName().equals("Language.java")) {
					final List<ExtractedString> extractedList = new Vector<ExtractedString>();
					extractStringsFromFile(dirFile, extractedList, currentFile);
					for (final ExtractedString current : extractedList) {
						if (!generatedMsgids.contains(current.getExtracted())) {
							writer.write(appendedMsgdIds(extractedList, current.getExtracted()));
							writer.write(MSGID + current.getExtracted() + "\"\r\n");
							writer.write("msgstr \"\"\r\n\r\n");
							generatedMsgids.add(current.getExtracted());
						}
					}
				}
			}
			writer.close();
		} catch (final IOException e) {
			JOptionPane.showMessageDialog(null, "Unexpected IO problem!");
		}

	}

	private static String appendedMsgdIds(List<ExtractedString> extractedList, String extracted) {
		String result = "";
		for (final ExtractedString current : extractedList) {
			if (current.getExtracted().equals(extracted)) {
				result += "#: " + current.getFilename() + ":" + current.getLineNumber() + "\r\n";
			}
		}
		return result;
	}

	private static void listJavaFiles(List<File> files, File fileDir) {
		final File[] fileList = fileDir.listFiles();
		for (final File currentFile : fileList) {
			if (currentFile.isDirectory()) {
				listJavaFiles(files, currentFile);
			} else {
				if (currentFile.getName().endsWith(".java"))
					files.add(currentFile);
			}
		}
	}

	private static void extractStringsFromFile(File rootDir, List<ExtractedString> extractedList, File file) {
		try {
			final BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),Charset.forName("UTF-8")));
			final String source = asString(reader);
			int offset = source.indexOf(MARK_START);
			while (offset > -1) {
				int pointer = offset + MARK_START.length();
				if (!Character.isJavaIdentifierPart(source.charAt(offset - 1))) {// make
					// make sure it does not match another method. ie.: somethingtranslate(
					final StringBuffer extracted = new StringBuffer();
					boolean insideBlockString = false;
					while (pointer < source.length()) {
						if (source.substring(pointer).startsWith(MARK_END) && (!insideBlockString))
							break;
						final char currentChar = source.charAt(pointer);
						if (currentChar == '\\'){
							if ((pointer+1)<source.length()){
								pointer++;
								final char specialChar = source.charAt(pointer);
								extracted.append(currentChar);
								extracted.append(specialChar);
							}
						}else if (currentChar == '\"'){
							insideBlockString = !insideBlockString;	
						}else if (insideBlockString)
							extracted.append(currentChar);
						pointer++;
					}
					final String name = file.getPath().substring(rootDir.getAbsolutePath().length() + 1) + File.separator + file.getName();
					final ExtractedString extractedString = new ExtractedString(name, countLineBreaks(source,offset), extracted.toString()); 
					extractedList.add(extractedString);
				}
				offset = source.indexOf(MARK_START, pointer);
			}
		} catch (final IOException e) {
			JOptionPane.showMessageDialog(null, "Unexpected IO problem!");
		}
	}
	
	private static int countLineBreaks(String source, int offset){
		int counter = 1;
		for(int t = 0; t<offset; t++){
			if (source.charAt(t)=='\n')
				counter++;
		}
		return counter;
	}

	private static String asString(BufferedReader reader) throws IOException {
		final StringBuffer buffer = new StringBuffer();
		String line = "";
		while ((line = reader.readLine()) != null) {
			buffer.append(line + "\n");
		}
		return buffer.toString();
	}

	private static class ExtractedString {
		private String _filename;

		private int _lineNumber;

		private String _extracted;

		public ExtractedString(String filename, int lineNumber, String extracted) {
			_filename = filename;
			_lineNumber = lineNumber;
			_extracted = extracted;
		}

		public void setFilename(String filename) {
			_filename = filename;
		}

		public String getFilename() {
			return _filename;
		}

		public void setLineNumber(int lineNumber) {
			_lineNumber = lineNumber;
		}

		public int getLineNumber() {
			return _lineNumber;
		}

		public void setExtracted(String extracted) {
			_extracted = extracted;
		}

		public String getExtracted() {
			return _extracted;
		}

	}

	private static void merge() { //Refactor: low priority. lots of redundant code in this class... unify...
		final JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setDialogTitle("Choose the Sources Directory");
		final int returnVal = chooser.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			final File dirFile = chooser.getSelectedFile();
			final String language_country = JOptionPane.showInputDialog("What language? (Examples: pt_BR, fr_FR )");

			try {
				final InputStream templateStreamIn = new FileInputStream(dirFile.getAbsolutePath() + File.separator + TRANSLATION_DIRECTORY + File.separator + TRANSLATION_FILENAME + ".pot");
				final InputStream languageStreamIn = new FileInputStream(dirFile.getAbsolutePath() + File.separator + TRANSLATION_DIRECTORY + File.separator + TRANSLATION_FILENAME + "_" + language_country + ".po");
				final OutputStream streamOut = new FileOutputStream(dirFile.getAbsolutePath() + TRANSLATION_DIRECTORY + TRANSLATION_FILENAME + "_" + language_country + ".po_merge");

				final List<String> msgids = new ArrayList<String>();
				final List<String> lines = new ArrayList<String>();
				final BufferedReader languageReader = new BufferedReader(new InputStreamReader(languageStreamIn,Charset.forName("UTF-8")));
				String line = "";
				while ((line = languageReader.readLine()) != null) {
					lines.add(line);
					if (line.startsWith(MSGID))
						msgids.add(line);
				}
				languageReader.close();

				final BufferedReader templateReader = new BufferedReader(new InputStreamReader(templateStreamIn, Charset.forName("UTF-8")));
				final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(streamOut, "UTF-8"));
				for (final String l : lines)
					writer.write(l + "\r\n");
				line = "";
				while ((line = templateReader.readLine()) != null) {
					if (line.startsWith(MSGID)) {
						if (!msgids.contains(line)) {
							writer.write(line + "\r\n");
							writer.write(templateReader.readLine() + "\r\n\r\n");
						}
					}
				}
				writer.close();
				templateReader.close();
				JOptionPane.showMessageDialog(null, "Done!");
			} catch (final FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, "Could not find Translation file. Do you generated it?");
			} catch (final IOException unexpected) {
				JOptionPane.showMessageDialog(null, "Unexpected IO problem!");
			}
		}
	}

}
