package kisti.server;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;


import org.xmlpull.v1.XmlPullParser;


import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


public class XmlTest extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);


        TextView txtView = (TextView) findViewById(R.id.TestTxtView01);


        try {
            // XML 처리에 사용할 변수 선언
            int event = 0;
            String currentTag = null;
            Stack<String> tagStack = new Stack<String>();


            // 스택 사용 여부! 값을 바꿔서 실행해 보시고 
            // <word>aaa<any>any text</any>bbb</word>에서 bbb의 결과를 확인해 보세요.
            boolean useStack = true;


            // 처리하고자 하는 TAG 등록
            List<String> tagList =
                Arrays.asList(new String[] {"number", "word", "mean", "any"});


            // XML 파서
            XmlPullParser parser = getResources().getXml(R.xml.test);


            // 파싱 시작
            while((event = parser.next()) != XmlPullParser.END_DOCUMENT) {


                switch(event) {


                case XmlPullParser.START_TAG:
                    // 시작 태그를 만나면 currentTag에 기록
                    if(useStack && currentTag != null) {
                        tagStack.push(currentTag);
                    }
                    currentTag = parser.getName();
                    break;


                case XmlPullParser.TEXT:
                    // currentTag가 처리하고자 하는 태그이면...
                    if(currentTag != null && tagList.contains(currentTag)) {
                        String value = parser.getText();
                        txtView.append(
                                "tag=" + currentTag + ", value=" + value + "\n");
                    }
                    break;


                case XmlPullParser.END_TAG:
                    // 종료 태그를 만나면 currentTag 초기화
                    if(useStack && tagStack.size() > 0) {
                        currentTag = tagStack.pop();
                    }
                    else {
                        currentTag = null;
                    }
                    break;


                default:
                    break;
                }
            }
        }
        catch(Throwable t) {}
    }
}