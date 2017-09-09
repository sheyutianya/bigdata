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
        // step:1 ����ģ�� 2 �������ƶ� 3 ����k���� 4 �����Ƽ�����  
        List<RecommendedItem> recommendations = null;  
        try {  
            //DataModel model = MyDataModel.myDataModel();//��������ģ�ͣ�Database-based  
        	DataModel model = new FileDataModel(new File("/home/huhui/movie_preferences.txt"));//��������ģ�ͣ�File-based  
            UserSimilarity similarity = new PearsonCorrelationSimilarity(model);//��PearsonCorrelation �㷨�����û����ƶ�  
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(3, similarity, model);//�����û��ġ��ھӡ������ｫ����û��������Ϊ 3 ���û�����Ϊ���û��ġ��ھӡ���  
            Recommender recommender = new CachingRecommender(new GenericUserBasedRecommender(model, neighborhood, similarity));//�����Ƽ����棬���� CachingRecommender Ϊ RecommendationItem ���л���  
            recommendations = recommender.recommend(userID, size);//�õ��Ƽ��Ľ����size���Ƽ��ӹ�����Ŀ  
        } catch (Exception e) {  
            // TODO: handle exception  
            e.printStackTrace();  
        }  
        return recommendations;  
    }  
  
  
    public static void main(String args[]) throws Exception {  
    	System.out.println( "Hello World!" );
    }  
}  