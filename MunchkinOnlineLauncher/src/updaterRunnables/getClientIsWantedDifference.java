package updaterRunnables;

import it.sauronsoftware.ftp4j.FTPAbortedException;
import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferException;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPFile;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;
import it.sauronsoftware.ftp4j.FTPListParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import utility.Constants;
import utility.Utility;

/**
 * This class determines the difference between the actual
 * client (version) and the wanted.
 * (Running in background)
 * 
 * @author Marius Kleiner
 * @version 0.1
 *
 */
public class getClientIsWantedDifference implements Runnable
{
	
	// Class variables
	private FTPClient client = new FTPClient();
	private String parentDir = null;
	
	/**
	 * Runnable
	 */
	public void run()
	{
		Utility.debugMsg("Determining difference between local and remote files.");
		// Open connection first
		openFTPConnection();
		// Gather a list of local and remote files
		File[] localFiles = getDataFolderFileList();
		FTPFile[] remoteFiles = getRemoteDataFolderFileListe();
		// Create empty download list
		ArrayList<FTPFile> downloadList = new ArrayList<FTPFile>();
		// Determine downloadList
		for (FTPFile ftpf : remoteFiles) {
			// This file might be new (or a newer version of existing file)
			downloadList.add(ftpf);
			// Compare to any local file....
			for (File lf : localFiles) {
				// Check if file exists already (local)
				if (compareFileNames(ftpf, lf)) { 
					// Don't load if it is up to date!
					if (compareFilesUniformDate(ftpf, lf)) {
						// Don't load this file
						downloadList.remove(ftpf);
					}
				}
			}
		}
		// Debug
		Utility.debugMsg("Downloading " + downloadList.size() + " Files...");
		// Debug File List
		Utility.debugMsg("List of FTP Files:");
		for (FTPFile ftpf : downloadList) {
			Utility.debugMsg("-> " + ftpf.getName());	
		}
		// Start downloading all files from downloadList
		downloadFiles(downloadList);
		
		// close Connection
		closeConnection();
	}

	/**
	 * Open ftp connection to update server
	 */
	private void openFTPConnection() {
		try
		{
			client.connect(Constants.updateHost);
			client.login(Constants.updateFTPuser, Constants.updateFTPpw);
			parentDir = client.currentDirectory();
		}
		catch (IllegalStateException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (FTPIllegalReplyException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (FTPException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * Try closing ftp connection
	 */
	private void closeConnection() {
		try
		{
			client.disconnect(true);
		}
		catch (IllegalStateException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (FTPIllegalReplyException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (FTPException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Downloads all files in list
	 * @param ftpFiles
	 */
	private void downloadFiles(ArrayList<FTPFile> ftpFiles) {
		Utility.debugMsg("Start downloading " + ftpFiles.size() + " files...");	
		// Create temp folder if not already exists
		File dir = new File(Constants.path_temp_folder);
		dir.delete();
		dir.mkdir();
		// Try downloading files one by one into temp folder
		try
		{
			// Change directory to download source
			client.changeDirectory(parentDir);
			client.changeDirectory(Constants.updateDataFolder);
			// Loop all files
			for (FTPFile f : ftpFiles) {
				Utility.debugMsg("-> Downloading File " + f.getName());
				client.download(f.getName(), new File(Constants.path_temp_folder + f.getName()));
			}
			Utility.debugMsg("Downloading files complete!");	
		}
		catch (IllegalStateException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (FTPIllegalReplyException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (FTPException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (FTPDataTransferException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (FTPAbortedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//client.d
		//
	}
	
	/**
	 * Compare the names of a remote and a local file
	 * @param ftpf
	 * @param lf
	 * @return
	 */
	private boolean compareFileNames(FTPFile ftpf, File lf) {
		if (ftpf.getName().equals(lf.getName())) {
			return true;
		}
		return false;
	}
	
	/**
	 * Compare last modified date of a remote and a local file
	 * @param ftpf
	 * @param lf
	 * @return
	 */
	private boolean compareFilesUniformDate(FTPFile ftpf, File lf) {
		if (!(ftpf.getModifiedDate().equals(lf.lastModified()))) {
			return false;
		}
		return true;
	}
	
	/**
	 * Check local files
	 * @return
	 */
	private File[] getDataFolderFileList() {
		Utility.debugMsg("Loading local files...");
		File folder = new File(Constants.path_data_folder);
		File[] listOfFiles = folder.listFiles();
		return listOfFiles;
	}
	
	/**
	 * Load a list of remote files in update folder
	 * @return
	 */
	private FTPFile[] getRemoteDataFolderFileListe() {
		Utility.debugMsg("Loading remote files...");
		// Try loading remote file list
		try
		{
			// Change directory to update source
			client.changeDirectory(parentDir);
			client.changeDirectory(Constants.updateDataFolder);
			// Get file list
			FTPFile[] files = client.list();
			// Convert to arrayList
			ArrayList<FTPFile> fileList = new ArrayList<FTPFile>();
			for (FTPFile ftpf : files) {
				fileList.add(ftpf);
			}
			// Delete ignorelist files
			for (FTPFile ftpf : files) {
				for (String s : Constants.remote_data_folder_ignorelist) {
					if (ftpf.getName().equals(s)) {
						fileList.remove(ftpf);
					}
				}
			}
			// Reconvert List to Array
			files = new FTPFile[fileList.size()];
			fileList.toArray(files);
			// Return
			return files;
		}
		catch (IllegalStateException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (FTPIllegalReplyException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (FTPException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (FTPDataTransferException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (FTPAbortedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (FTPListParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
