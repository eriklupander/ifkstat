package se.ifkgoteborg.stat.transformer;

import java.io.File;

import junit.framework.Assert;

import org.junit.Test;

import se.ifkgoteborg.stat.importer.transformer.XlsTransformer;

public class XlsTransformerTest {

	@Test
	public void test2008File() {
		File file = new File(XlsTransformer.ROOT_FOLDER + "\\År för år\\2004-2011\\IFK statistik år 2008.xls");
		XlsTransformer xlsTransformer = new XlsTransformer();
		xlsTransformer.processFile(file);
		
		String output = xlsTransformer.getBuffer();
		Assert.assertTrue(output != null);
		Assert.assertTrue(output.length() > 0);
		System.out.println(output);
	}
}
