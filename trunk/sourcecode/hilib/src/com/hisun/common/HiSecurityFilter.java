package com.hisun.common;


import com.hisun.exception.HiException;
import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;


public class HiSecurityFilter {
    private ArrayList _excludeList;
    private ArrayList _includeList;
    private URL _url;
    private long _lastModified;
    private String _file;
    private Logger _log;


    public HiSecurityFilter() {

        this._excludeList = new ArrayList();

        this._includeList = new ArrayList();

        this._url = null;

        this._lastModified = -1L;

        this._file = null;

        this._log = HiLog.getLogger("SYS.trc");
    }


    public void setUrl(URL url) {

        this._url = url;

    }


    public void setFile(String file) {

        this._file = file;

    }


    public void load() throws HiException {

        loadFilterURL(this._url);

    }


    public void loadFilterURL(URL url) throws HiException {

        Element element;

        String value;

        if (this._log.isDebugEnabled()) {

            this._log.debug("loadFilterURL:[" + url + "]");

        }


        this._excludeList.clear();

        this._includeList.clear();

        this._url = url;

        SAXReader reader = new SAXReader();

        Document doc = null;

        try {

            doc = reader.read(url);

        } catch (Exception e) {

            throw new HiException(e);

        }

        Element root = doc.getRootElement();

        Element include = root.element("include");

        Element exclude = root.element("exclude");

        Iterator iter = null;

        if (include != null) {

            iter = include.elementIterator();

            while (iter.hasNext()) {

                element = (Element) iter.next();


                value = element.attributeValue("value");

                if (StringUtils.isBlank(value)) {

                    continue;

                }

                this._includeList.add(value);

            }

        }


        if (exclude != null) {

            iter = exclude.elementIterator();

            while (iter.hasNext()) {

                element = (Element) iter.next();


                value = element.attributeValue("value");

                if (StringUtils.isBlank(value)) {

                    continue;

                }

                this._excludeList.add(value);

            }

        }

        if (this._log.isDebugEnabled())
            this._log.debug("exclude:" + this._excludeList + "; include:" + this._includeList);

    }


    public void reloaded() throws HiException {

        if (isNeedReloaded(this._file)) loadFilterURL(this._url);

    }


    public boolean isNeedReloaded(String file) {

        if (this._log.isDebugEnabled()) {

            this._log.debug("file:[" + file + "]");

        }


        File f = new File(file);

        this._log.info("lastModified:[" + f.lastModified() + "]");

        if (f.lastModified() != this._lastModified) {

            this._lastModified = f.lastModified();

            return true;

        }

        return false;

    }


    public boolean contains(String value) throws HiException {

        String tmp;

        reloaded();


        for (int i = 0; i < this._includeList.size(); ++i) {

            tmp = (String) this._includeList.get(i);

            if (this._log.isDebugEnabled()) {

                this._log.debug("include[" + value + "]:[" + tmp + "]");

            }

            if (value.matches(tmp)) {

                return true;

            }

        }


        for (i = 0; i < this._excludeList.size(); ++i) {

            tmp = (String) this._excludeList.get(i);

            if (this._log.isDebugEnabled()) {

                this._log.debug("exclude: [" + value + "]:[" + tmp + "]");

            }

            if (value.matches(tmp)) {

                return false;

            }

        }

        if (this._log.isDebugEnabled()) {

            this._log.debug("contains: empty");

        }


        return (this._includeList.size() != 0);

    }

}