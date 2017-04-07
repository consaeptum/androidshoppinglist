package com.corral.androidshoppinglist;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import persistencia.dao.DBHelper;
import persistencia.dao.LineaContract;
import persistencia.dao.LineaDao;
import persistencia.jb.Linea;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static junit.framework.Assert.assertTrue;

/**
 *
 */
@RunWith(AndroidJUnit4.class)
public class LineaDaoTest {

    private DBHelper mDBHelper;

    @Before
    public void setUp() throws Exception {
        mDBHelper = new DBHelper(LineaContract.getInstance(), getTargetContext());
        getTargetContext().deleteDatabase(mDBHelper.getDatabaseName());
    }

    @After
    public void tearDown() throws Exception {
        mDBHelper.close();
    }

    @Test
    public void crudTest() throws Exception {

        Context appContext = getTargetContext();

        Linea f = new Linea();
        LineaDao fd = new LineaDao();

        f.setCantidad(44f);
        assertTrue(fd.insert(appContext, f));

        f = fd.read(appContext, f.getId());
        assertTrue(f != null);

        f.setCantidad(88f);
        fd.update(appContext, f);

        f = fd.read(appContext, f.getId());
        assertTrue(f.getCantidad() == 88f);

        assertTrue(fd.delete(appContext, f.getId()));

    }

}
