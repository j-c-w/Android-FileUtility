package fileutility;

import java.io.*;

/**
 * @author Jackson Woodruff
 *
 * This is a simple file utility for Android that
 * manages some basic file operations.
 *
 * Includes methods for things such as read, write, move, copy etc.
 */
public class FileUtility {
	public static String read(File file) throws IOException {
		StringBuilder contents = new StringBuilder();

		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		String line;

		while ((line = bufferedReader.readLine()) != null) {
			contents.append(line);
			contents.append('\n');
		}
		bufferedReader.close();

		return contents.toString();
	}

	public static void write(String contents, File file) throws IOException {
		if (!file.exists() && !file.createNewFile()) {
			throw new IOException("Error -- Could not create file " + file.toString());
		}

		FileOutputStream outputStream = new FileOutputStream(file);
		outputStream.write(contents.getBytes());
		outputStream.close();
	}

	public static void move(File src, File dest) throws IOException{
		if (!src.exists()) {
			throw new IOException("Source file " + src.toString() + " does not exist");
		}

		if (dest.exists()) {
			if (dest.delete()) {
				throw new IOException("Failed to clear destination file before writing. Do you have" +
						" permission to delete the destination file?");
			}
		}

		if (!src.renameTo(dest)) {
			throw new IOException("Error -- Could not rename " + src.toString() + " to " + dest.toString());
		}
	}

	public static void copy(File src, File dest) throws IOException {
		if (!src.exists()) {
			throw new IOException("Source file " + src.toString() + " does not exist");
		}

		if (dest.exists()) {
			if (dest.delete()) {
				throw new IOException("Failed to clear destination file before writing. Do you have" +
						" permission to delete the destination file?");
			}
		}

		InputStream in = new FileInputStream(src);
		OutputStream out = new FileOutputStream(dest);

		// Transfer bytes from in to out
		byte[] buffer = new byte[1024];
		int len;

		while ((len = in.read(buffer)) > 0) {
			out.write(buffer, 0, len);
		}

		in.close();
		out.close();
	}

	public static String getExtension(File file) {
		return getExtension(file.getName());
	}

	public static String getExtension(String fileName) {
		int index = fileName.lastIndexOf('.');

		// lastIndexOf returns -1 on no occurance of the . character
		// And if the file ends in a . then substring would through an
		// index out of bounds exception.
		if (index == -1 || index == fileName.length() - 1) {
			return "";
		}

		return fileName.substring(index + 1, fileName.length());
	}

	public static String getNameWithoutExtension(File file) {
		return getNameWithoutExtension(file.getName());
	}

	public static String getNameWithoutExtension(String fileName) {
		int index = fileName.lastIndexOf('.');

		if (index == -1 || index == 0) {
			return "";
		}

		return fileName.substring(0, index);
	}
}
