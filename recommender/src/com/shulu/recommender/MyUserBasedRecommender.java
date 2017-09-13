package com.shulu.recommender;

import java.io.File;
import java.util.List;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class MyUserBasedRecommender {  
    public List<RecommendedItem> userBasedRecommender(long userID,int size) {  
        // step:1 构建模型 2 计算相似度 3 查找k紧邻 4 构造推荐引擎  
        List<RecommendedItem> recommendations = null;  
        try {  
            //DataModel model = MyDataModel.myDataModel();//构造数据模型，Database-based  
        	DataModel model = new FileDataModel(new File("data/ratings.csv"));//构造数据模型，File-based  
            UserSimilarity similarity = new PearsonCorrelationSimilarity(model);//用PearsonCorrelation 算法计算用户相似度  
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(3, similarity, model);//计算用户的“邻居”，这里将与该用户最近距离为 3 的用户设置为该用户的“邻居”。  
            Recommender recommender = new CachingRecommender(new GenericUserBasedRecommender(model, neighborhood, similarity));//构造推荐引擎，采用 CachingRecommender 为 RecommendationItem 进行缓存  
            recommendations = recommender.recommend(userID, size);//得到推荐的结果，size是推荐接过的数目  
        } catch (Exception e) {  
            // TODO: handle exception  
            e.printStackTrace();  
        }  
        return recommendations;  
    }  
  
  
    public static void main(String args[]) throws Exception {  
    	
    	File ss = new File("res/ratings.csv");
    	
    	System.out.println(ss.getPath());
    	
    	System.out.println( "Hello World!" );
    	
    	System.out.println(Class.class.getClass().getResource("/").getPath());
    	
    	MyUserBasedRecommender temp = new MyUserBasedRecommender();
    	List<RecommendedItem> templist = temp.userBasedRecommender(1, 2);  	
    	System.out.println(templist.size());
    }  
}  