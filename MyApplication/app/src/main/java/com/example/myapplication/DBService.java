package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

public class DBService {

    public List<Question> getQuestion(){
        List<Question> list = new ArrayList<Question>();

        Question question1 = new Question();
        question1.ID = 0;
        question1.question = "\"海内存知己，天涯若比邻\" is a verse from ( )?";
        question1.answerA = "王勃";
        question1.answerB = "李白";
        question1.answerC = "白居易";
        question1.answer = 0;
        question1.explaination = "\"海内存知己，天涯若比邻\" comes from 《送杜少府之任蜀州》. " +
                "This poem is the work of Tang Dynasty poet 王勃 (Wang Bo).\n\n" +
                "The full poem is: \n" +
                "《送杜少府之任蜀州》 王勃 \n"+
                "城阙辅三秦，风烟望五津。 \n" +
                "与君离别意，同是宦游人。 \n" +
                "海内存知己，天涯若比邻。 \n" +
                "无为在歧路，儿女共沾巾。 \n";
        question1.selectedAnswer = -1;

        list.add(question1);

        Question question2 = new Question();
        question2.ID = 1;
        question2.question = "但使龙城飞将在，不教胡马度( )";
        question2.answerA = "燕山";
        question2.answerB = "边关";
        question2.answerC = "阴山";
        question2.answer = 2;
        question2.explaination = "\"但使龙城飞将在，不教胡马度阴山\" comes from 《出塞二首·其一》. " +
                "This poem is the work of Tang Dynasty poet 王昌龄 (Wang Changling).\n\n" +
                "The full poem is: \n" +
                "秦时明月汉时关，万里长征人未还。\n" +
                "但使龙城飞将在，不教胡马度阴山。\n";
        question2.selectedAnswer = -1;

        list.add(question2);

        Question question3 = new Question();
        question3.ID = 2;
        question3.question = "What does \n\"忽如一夜春风来，千树万树梨花开\" \ndescribe?";
        question3.answerA = "Spring scenery";
        question3.answerB = "Pear blossom";
        question3.answerC = "Snow scape";
        question3.answer = 2;
        question3.explaination = "\"忽如一夜春风来，千树万树梨花开\" comes from 《白雪歌送武判官归京》. " +
                "This poem is the work of Tang Dynasty poet 岑参 (Cen Shen).\n\n" +
                "The full poem is: \n" +
                "北风卷地白草折，胡天八月即飞雪。\n"+
                "忽如一夜春风来，千树万树梨花开。\n"+
                "散入珠帘湿罗幕，狐裘不暖锦衾薄。\n"+
                "将军角弓不得控，都护铁衣冷难着。\n"+
                "瀚海阑干百丈冰，愁云惨淡万里凝。\n"+
                "中军置酒饮归客，胡琴琵琶与羌笛。\n"+
                "纷纷暮雪下辕门，风掣红旗冻不翻。\n"+
                "轮台东门送君去，去时雪满天山路。\n"+
                "山回路转不见君，雪上空留马行处。\n";
        question3.selectedAnswer = -1;

        list.add(question3);
        list.add(question1);
        list.add(question2);
        list.add(question3);
        list.add(question1);
        list.add(question2);
        list.add(question3);
        list.add(question1);

        return list;
    }

}
