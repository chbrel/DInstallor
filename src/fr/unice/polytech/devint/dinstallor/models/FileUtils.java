package fr.unice.polytech.devint.dinstallor.models;

import java.io.*;

import fr.unice.polytech.devint.dinstallor.views.InstallingView;

/**
 * 
 * From: http://forum.hardware.fr/hfr/Programmation/Java/peut-repertoire-entier-sujet_44879_1.htm
 * 
 * @author Benou
 * 
 * Ptites modifs pour le projet: @author Christian Brel @author Romaric Pighetti
 * 
 */
public class FileUtils {

	/**
	 * @author Christian Brel
	 * @author Romaric Pighetti
	 */
	public static InstallingView iv = null;
	
	/**
	 * @author Christian Brel
	 * @author Romaric Pighetti
	 */
	public static void write(String path, String content) {
		iv.concat("Ecriute de " + path);
		try {
			FileWriter fw = new FileWriter(path, true);
			BufferedWriter output = new BufferedWriter(fw);
			output.write(content);
			output.flush();
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void copy(final InputStream inStream, final OutputStream outStream, final int bufferSize) throws IOException {
		final byte[] buffer = new byte[bufferSize];
		int nbRead;
		while ((nbRead = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, nbRead);
		}
	}

	public static void copyDirectory(final File from, final File to) throws IOException {
		iv.concat("-- Copie de répertoire: de " + from.getAbsolutePath() + " vers " + to.getAbsolutePath() + " --");
		
		if (! to.exists()) {
			to.mkdir();
		}
		final File[] inDir = from.listFiles();
		for (int i = 0; i < inDir.length; i++) {
			final File file = inDir[i];
			copy(file, new File(to, file.getName()));
		}
	}
	
	public static void copyFile(final File from, final File to) throws IOException {
		iv.concat("Copie de fichier: de " + from.getAbsolutePath() + " vers " + to.getAbsolutePath());
		
		final InputStream inStream = new FileInputStream(from);
		final OutputStream outStream = new FileOutputStream(to);
		if (from.length() > 0){ 
			copy(inStream, outStream, (int) Math.min(from.length(), 4*1024));
		}
		inStream.close();
		outStream.close();
	}
	
	public static void copy(final File from, final File to) throws IOException {
		if (from.isFile()) {
			copyFile(from, to);
		} else if (from.isDirectory()){
			copyDirectory(from, to);
		} else {
			throw new FileNotFoundException(from.toString() + " does not exist" );
		}
	} 
}