package de.myreality.minecraft.minelog;

import java.io.BufferedReader;
import java.io.Externalizable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Configuration for a single message
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.2
 * @version 1.2
 */
public class MessageConfiguration implements Externalizable {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private List<String> files;

	// ===========================================================
	// Constructors
	// ===========================================================

	public MessageConfiguration() {
		this.files = new ArrayList<String>();
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public void setFiles(String[] files, MineLogPlugin plugin) {
		this.files.clear();
		for (String f : files) {
			if (f != null && !f.isEmpty()) {
				this.files.add(f);
			}
		}
	}
	
	

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.Externalizable#readExternal(java.io.ObjectInput)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		files = (List<String>) in.readObject();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.Externalizable#writeExternal(java.io.ObjectOutput)
	 */
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(files);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public List<String> loadMessages(JavaPlugin plugin) {
		List<String> messages = new ArrayList<String>();
		for (String f : files) {
			File file = new File(plugin.getDataFolder(), f);
			messages.addAll(scanLocal(file));
		}
		return messages;
	}
	
	private List<String> scanLocal(File file) {
		
		List<String> messages = new ArrayList<String>();
		InputStream stream = null;
		BufferedReader reader = null;
		String line;

		try {
			stream = new FileInputStream(file);
			reader = new BufferedReader(
					new InputStreamReader(stream));
			while ((line = reader.readLine()) != null) {
				messages.add(line);
			}			
		} catch (IOException e) {
			
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return messages;
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
