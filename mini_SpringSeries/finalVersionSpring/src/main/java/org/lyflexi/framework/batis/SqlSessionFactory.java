package org.lyflexi.framework.batis;

public interface SqlSessionFactory {
	SqlSession openSession();
	MapperNode getMapperNode(String name);
}
