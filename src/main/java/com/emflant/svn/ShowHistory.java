package com.emflant.svn;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.table.DefaultTableModel;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

public class ShowHistory {
	
	public void print(){
		
		
		DAVRepositoryFactory.setup();
		
		
		String url = "https://172.30.1.7/svn/Finance/";
		String name = "admin";
		String password = "answodl";
		
		
		
		long startRevision = 0;
		long endRevision = -1;
		
		
		SVNRepository repository = null;
		
		
		
		try {
			repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));
			ISVNAuthenticationManager authenticationManager = SVNWCUtil.createDefaultAuthenticationManager(name, password);
			repository.setAuthenticationManager(authenticationManager);
			
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date baseDate = null;
			try {
				baseDate = sdf.parse("2012-03-02 00:00:00");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			startRevision = repository.getDatedRevision(baseDate);
			
			Collection logEntries = repository.log(new String[]{""}, null, startRevision, endRevision, true, true);
			Iterator entries = logEntries.iterator();
			
			System.out.println("start : "+repository.getDatedRevision(baseDate));
			
			int i=0;
			
			while(entries.hasNext()){
				SVNLogEntry logEntry = (SVNLogEntry)entries.next();
				System.out.println(sdf.format(logEntry.getDate()) + " (ver."+ logEntry.getRevision() +") : "+logEntry.getMessage().replace("\n", ""));
				
				Map<String, SVNLogEntryPath> map =  logEntry.getChangedPaths();
				Set<String> changedPathSet = map.keySet();
				
				Iterator changedPaths = changedPathSet.iterator();
				
				while(changedPaths.hasNext()){
					String key = (String)changedPaths.next();
					SVNLogEntryPath entryPath = (SVNLogEntryPath)map.get(key);
					//System.out.println("\t"+entryPath);
				}
			}
			
			
		} catch (SVNException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	public DefaultTableModel getHistoryByDefaultTableModel(){
		DefaultTableModel lResult = new DefaultTableModel();
		DAVRepositoryFactory.setup();
		
		
		String url = "https://172.30.1.7/svn/Finance/";
		String name = "admin";
		String password = "answodl";
		
		
		
		long startRevision = 0;
		long endRevision = -1;
		
		
		SVNRepository repository = null;
		
		
		
		try {
			repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));
			ISVNAuthenticationManager authenticationManager = SVNWCUtil.createDefaultAuthenticationManager(name, password);
			repository.setAuthenticationManager(authenticationManager);
			
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date baseDate = null;
			try {
				baseDate = sdf.parse("2011-10-02 00:00:00");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			startRevision = repository.getDatedRevision(baseDate);
			
			Collection logEntries = repository.log(new String[]{""}, null, startRevision, endRevision, true, true);
			Iterator entries = logEntries.iterator();
			
			System.out.println("start : "+repository.getDatedRevision(baseDate));
			
			String[] row = null;
			
			while(entries.hasNext()){
				SVNLogEntry logEntry = (SVNLogEntry)entries.next();
				System.out.println(sdf.format(logEntry.getDate()) + " (ver."+ logEntry.getRevision() +") : "+logEntry.getMessage().replace("\n", ""));
				
				String[] columns = new String[3];
				columns[0] = "date";
				columns[1] = "version";
				columns[2] = "comment";
				lResult.setColumnIdentifiers(columns);
				
				row = new String[3];
				row[0] = sdf.format(logEntry.getDate());
				row[1] = String.valueOf(logEntry.getRevision());
				row[2] = logEntry.getMessage().replace("\n", " ");
				
				
				lResult.addRow(row);
				
				
				Map<String, SVNLogEntryPath> map =  logEntry.getChangedPaths();
				Set<String> changedPathSet = map.keySet();
				
				Iterator changedPaths = changedPathSet.iterator();
				
				while(changedPaths.hasNext()){
					String key = (String)changedPaths.next();
					SVNLogEntryPath entryPath = (SVNLogEntryPath)map.get(key);
					//System.out.println("\t"+entryPath);
				}
			}
			
			
		} catch (SVNException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return lResult;
	}
	
	
	public static void main(String[] args){
		
		
		ShowHistory sh = new ShowHistory();
		sh.getHistoryByDefaultTableModel();
	}

}
