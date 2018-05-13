package com.xx.watermelon.common.utils.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.List;
import java.util.Properties;

/**
 * @description Java邮件发送服务
 * @version 1.0,2017-4-1 下午4:07:01,Liugl,Ins
 * @remark
 */
public class EmailUtils {
	
	private static final Logger LOG = LoggerFactory.getLogger(EmailUtils.class);
	private MimeMessage mimeMsg;		//MIME邮件对象
	private Session session;			//邮件会话对象
	private Properties props;			//系统属性
	private boolean needAuth = false;	//smtp是否需要认证
	private String username;			//smtp认证用户名和密码
	private String password;
	private Multipart mp;				//Multipart对象,邮件内容,标题,附件等内容均添加到其中后再生成

	public EmailUtils() {
	}

	public EmailUtils(String smtp) {
		//配置javax.mail.Session对象
		setSmtpHost(smtp);
		//配置使用验证
		setNeedAuth(!needAuth);
		//创建邮件
		createMimeMessage();
	}

	/**
	 * 配置javax.mail.Session对象
	 * @param hostName
	 */
	public void setSmtpHost(String hostName) {
		if (props == null)
			props = System.getProperties();				//获得系统属性对象
		props.put("mail.smtp.host", hostName);			//设置SMTP主机
//		props.put("mail.smtp.starttls.enable", "true");	//使用 STARTTLS安全连接
//		props.put("mail.smtp.port", "25");				//google使用465或587端口
//		props.put("mail.smtp.localhost", "localHostAdress");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");//设置SSL连接
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.port", "465");
	}
	/**
	 * 设置smtp身份认证
	 * @param need
	 */
	public void setNeedAuth(boolean need) {
		if (props == null)
			props = System.getProperties();
		if (need) {
			props.put("mail.smtp.auth", "true");
		} else {
			props.put("mail.smtp.auth", "false");
		}
	}
	/**
	 * 设置用户名与密码
	 * @param name
	 * @param pass
	 */
	public void setNamePass(String name, String pass) {
		username = name;
		password = pass;
	}

	/**
	 * 创建邮件
	 * @return
	 */
	public boolean createMimeMessage() {
		try {
			//获得邮件会话对象
			session = Session.getDefaultInstance(props, null);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		try {
			mimeMsg = new MimeMessage(session);	//创建MIME邮件对象
			mp = new MimeMultipart();			//一个multipart对象
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 设置邮件主题
	 * @param mailSubject
	 * @return
	 */
	public boolean setSubject(String mailSubject) {
		try {
			mimeMsg.setSubject(MimeUtility.encodeText(mailSubject, "utf-8", "B"));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 设置邮件体格式
	 * @param mailBody
	 * @return
	 */
	public boolean setBody(String mailBody) {
		try {
			BodyPart bp = new MimeBodyPart();
			//转换成中文格式
			bp.setContent("<meta http-equiv=Content-Type content=text/html;charset=utf-8>" + mailBody, "text/html;charset=utf-8");
			mp.addBodyPart(bp);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 设置附件
	 * @param files
	 * @return
	 */
	public boolean setFiles(List<String> files) {
		try {
			for (String s : files) {
				MimeBodyPart mbp = new MimeBodyPart();
				FileDataSource fds = new FileDataSource(s);	//得到数据源
				mbp.setDataHandler(new DataHandler(fds));	//得到附件本身并至入BodyPart
				mbp.setFileName(fds.getName());				//得到文件名同样至入BodyPart
				mp.addBodyPart(mbp);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 添加附件
	 * @param path
	 * @param name
	 * @return
	 */
	public boolean addFile(String path, String name) {
		try {
			MimeBodyPart mbp = new MimeBodyPart();
			FileDataSource fds = new FileDataSource(path); // 得到数据源
			mbp.setDataHandler(new DataHandler(fds)); // 得到附件本身并至入BodyPart
			mbp.setFileName(MimeUtility.encodeText(name, "utf-8", "B"));
			mp.addBodyPart(mbp);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 设置发信人
	 * @param from
	 * @return
	 */
	public boolean setFrom(String from) {
		try {
			mimeMsg.setFrom(new InternetAddress(from)); // 设置发信人
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 设置收信人
	 * @param to
	 * @return
	 */
	public boolean setTo(String to) {
		if (to == null)
			return false;
		try {
			mimeMsg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 抄送
	 * @param copyto
	 * @return
	 */
	public boolean setCopyTo(String copyto) {
		if (copyto == null)
			return false;
		try {
			mimeMsg.setRecipients(Message.RecipientType.CC, (Address[]) InternetAddress.parse(copyto));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 邮件发送
	 * @return
	 */
	public boolean sendout() {
		try {
			mimeMsg.setContent(mp);
			mimeMsg.saveChanges();
			LOG.info("正在发送邮件....");
			Session mailSession = Session.getInstance(props, null);
			Transport transport = mailSession.getTransport("smtp");
			transport.connect((String) props.get("mail.smtp.host"), username, password);
			transport.sendMessage(mimeMsg, mimeMsg.getRecipients(Message.RecipientType.TO));
			LOG.info("发送邮件成功！");
			transport.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("邮件发送失败！" + e);
			return false;
		}
	}
	
	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(">>>邮件发送开始>>>");
		EmailUtils utils = new EmailUtils("smtp.ym.163.com");
		utils.setFrom("services@7atour.com");
		utils.setNamePass("services@7atour.com", "FLbdDTczRZ");
		utils.setSubject("测试");
		utils.setBody("测试邮件");
		utils.setTo("429281723@qq.com");
		utils.sendout();
		System.out.println("<<<邮件发送结束<<<");
	}
}