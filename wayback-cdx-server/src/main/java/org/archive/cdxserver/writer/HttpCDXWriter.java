package org.archive.cdxserver.writer;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public abstract class HttpCDXWriter extends CDXWriter {

	public final static String X_NUM_PAGES = "X-CDX-Num-Pages";
	public final static String X_MAX_LINES = "X-CDX-Max-Lines";

	protected HttpServletResponse response;
	protected PrintWriter writer;
	
	public HttpCDXWriter(HttpServletResponse response) throws IOException {
	    this.response = response;
	    this.writer = response.getWriter();
    }
	
	@Override
	public void close()
	{
		writer.flush();
		writer.close();
	}
	
	@Override
	public boolean checkError()
	{
		return writer.checkError();
	}

	@Override
    public void printError(String msg) {
	    response.setStatus(400);
	    writer.println(msg);
	    writer.flush();
    }
	
	@Override
	public void setContentType(String type)
	{
		response.setContentType(type);
	}

	@Override
    public void setMaxLines(int maxLines) {
		response.setHeader(X_MAX_LINES, "" + maxLines);
    }
	
	//TODO: remove this eventually, used for idx output
	@Override
	public void writeMiscLine(String line)
	{
		writer.println(line);
	}

	@Override
    public void printNumPages(int numPages, boolean printInBody) {
		response.setHeader(X_NUM_PAGES, "" + numPages);
		if (printInBody) {
			writer.println(numPages);
		}
    }
}