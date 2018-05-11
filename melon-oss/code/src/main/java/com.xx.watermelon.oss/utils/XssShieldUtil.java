package com.zc.travel.webboss.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 处理非法字符
 *
 * @author   单红宇(365384722)
 * @myblog  http://blog.csdn.net/catoop/
 * @create    2015年9月18日
 */
public class XssShieldUtil {

    private static List<Pattern> patterns = null;

    private static List<Object[]> getXssPatternList() {
        List<Object[]> ret = new ArrayList<Object[]>();

        ret.add(new Object[]{"<(no)?script[^>]*>.*?</(no)?script>", Pattern.CASE_INSENSITIVE});
        ret.add(new Object[]{"eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL});
        ret.add(new Object[]{"expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL});
        ret.add(new Object[]{"(javascript:|vbscript:|view-source:)*", Pattern.CASE_INSENSITIVE});
        ret.add(new Object[]{"<(\"[^\"]*\"|\'[^\']*\'|[^\'\">])*>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL});
        ret.add(new Object[]{"(window\\.location|window\\.|\\.location|document\\.cookie|document\\.|alert\\(.*?\\)|window\\.open\\()*", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL});
        ret.add(new Object[]{"<+\\s*\\w*\\s*(oncontrolselect|script|oncopy|oncut|ondataavailable|ondatasetchanged"
        		+ "|ondatasetcomplete|ondblclick|ondeactivate|ondrag|ondragend|ondragenter|ondragleave"
        		+ "|ondragover|ondragstart|ondrop|onerror=|onerroupdate|onfilterchange|onfinish|onfocus"
        		+ "|onfocusin|onfocusout|onhelp|onkeydown|onkeypress|onkeyup|onlayoutcomplete|onload"
        		+ "|onlosecapture|onmousedown|onmouseenter|onmouseleave|onmousemove"
        		+ "|onmousout|onmouseover|onmouseup|onmousewheel|onmove|onmoveend|onmovestart|onabort"
        		+ "|onactivate|onafterprint|onafterupdate|onbefore|onbeforeactivate|onbeforecopy|onbeforecut"
        		+ "|onbeforedeactivate|onbeforeeditocus|onbeforepaste|onbeforeprint|onbeforeunload|onbeforeupdate"
        		+ "|onblur|onbounce|oncellchange|onchange|onclick|oncontextmenu|onpaste|onpropertychange|onreadystatechange"
        		+ "|onreset|onresize|onresizend|onresizestart|onrowenter|onrowexit|onrowsdelete|onrowsinserted|onscroll"
        		+ "|onselect|onselectionchange|onselectstart|onstart|onstop|onsubmit|onunload|javascript"
        		+ "|vbscript|view-source)+\\s*=+", 
        		Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL});
        return ret;
    }

    private static List<Pattern> getPatterns() {

        if (patterns == null) {

            List<Pattern> list = new ArrayList<Pattern>();

            String regex = null;
            Integer flag = null;
            int arrLength = 0;

            for(Object[] arr : getXssPatternList()) {
                arrLength = arr.length;
                for(int i = 0; i < arrLength; i++) {
                    regex = (String)arr[0];
                    flag = (Integer)arr[1];
                    list.add(Pattern.compile(regex, flag));
                }
            }

            patterns = list;
        }

        return patterns;
    }

    public static String stripXss(String value) {
        if(StringUtils.isNotBlank(value)) {

            Matcher matcher = null;

            for(Pattern pattern : getPatterns()) {
                matcher = pattern.matcher(value);
                // 匹配
                if(matcher.matches()) {
                    //半角转全角
                	value = ToSBC(value);
                	break;
                }
            }
            //value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        }
        return value;
    }
    
    /**
    * 半角转全角
    * @param input String.
    * @return 全角字符串.
    */  
    public static String ToSBC(String input) {   
    	char c[] = input.toCharArray();   
        for (int i = 0; i < c.length; i++) {   
        	if (c[i] == ' ') {   
            	c[i] = '\u3000';   //采用十六进制,相当于十进制的12288
            } else if (c[i] < '\177') {    //采用八进制,相当于十进制的127   
            	c[i] = (char) (c[i] + 65248);   
      
            }   
        }   
        return new String(c);
    }

    public static void main(String[] args) {
        String value = null;
        value = XssShieldUtil.stripXss("<script language=text/javascript>alert(document.cookie);</script>");
        System.out.println("type-1: '" + value + "'");

        value = XssShieldUtil.stripXss("<script src='' onerror='alert(document.cookie)'></script>");
        System.out.println("type-2: '" + value + "'");

        value = XssShieldUtil.stripXss("</script>");
        System.out.println("type-3: '" + value + "'");

        value = XssShieldUtil.stripXss(" eval(abc);");
        System.out.println("type-4: '" + value + "'");

        value = XssShieldUtil.stripXss(" expression(abc);");
        System.out.println("type-5: '" + value + "'");

        value = XssShieldUtil.stripXss("<img src='' onerror='alert(document.cookie);'></img>");
        System.out.println("type-6: '" + value + "'");

        value = XssShieldUtil.stripXss("<img src='' onerror='alert(document.cookie);'/>");
        System.out.println("type-7: '" + value + "'");

        value = XssShieldUtil.stripXss("<img src='' onerror='alert(document.cookie);'>");
        System.out.println("type-8: '" + value + "'");

        value = XssShieldUtil.stripXss("<script language=text/javascript>alert(document.cookie);");
        System.out.println("type-9: '" + value + "'");

        value = XssShieldUtil.stripXss("<script>window.location='url'");
        System.out.println("type-10: '" + value + "'");

        value = XssShieldUtil.stripXss(" onload='alert(\"abc\");");
        System.out.println("type-11: '" + value + "'");

        value = XssShieldUtil.stripXss("<img src=x<!--'<\"-->>");
        System.out.println("type-12: '" + value + "'");

        value = XssShieldUtil.stripXss("<=img onstop=");
        System.out.println("type-13: '" + value + "'");

        value = XssShieldUtil.stripXss("id");
        System.out.println("type-14: '" + value + "'");
    }
}