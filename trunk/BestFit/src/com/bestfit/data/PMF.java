package com.bestfit.data;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

public final class PMF {
	
	private static final PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("nontransactional-datasource");
	
	public static PersistenceManagerFactory get(){
	
		return pmf;
	}

}
