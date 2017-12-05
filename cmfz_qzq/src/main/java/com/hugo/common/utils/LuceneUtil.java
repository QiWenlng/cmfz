package com.hugo.common.utils;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;

/**
 * 操作索引库工具类
 */
public class LuceneUtil {
    private static Directory directory;
    private static IndexWriterConfig config;
    private static Analyzer analyzer;
    private static Version version;

    static {
        try {
            //指定lucene版本号
            version = Version.LUCENE_44;
            //创建索引对象  参数:索引存放位置
            directory = FSDirectory.open(new File("E:\\lucene\\index"));
            //创建分词器, StandardAnalyzer 标准分词器
            analyzer = new StandardAnalyzer(version);
            config = new IndexWriterConfig(version, analyzer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取IndexWriter对象的方法
     *
     * @return
     */
    public static IndexWriter getIndexWriter() {
        IndexWriter indexWriter = null;
        try {
            indexWriter = new IndexWriter(directory, config);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return indexWriter;
    }

    /**
     * 获取IndexSearcher对象
     *
     * @return
     */
    public static IndexSearcher getIndexSearcher() {
        IndexSearcher indexSearcher = null;
        try {
            DirectoryReader reader = DirectoryReader.open(directory);
            indexSearcher = new IndexSearcher(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return indexSearcher;
    }

    /**
     * 提交indexWriter
     */
    public static void commitIndexWriter(IndexWriter indexWriter) {
        if (indexWriter != null) {
            try {
                indexWriter.commit();
                indexWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 回滚indexWriter
     *
     * @param indexWriter
     */
    public static void rollbackIndexWriter(IndexWriter indexWriter) {
        if (indexWriter != null) {
            try {
                indexWriter.rollback();
                indexWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
