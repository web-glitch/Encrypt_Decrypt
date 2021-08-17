

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*;
import java.math.BigInteger;

public class Decrypt extends JFrame {

	private JPanel contentPane;
	private JTextField t1;
	private JTextField t2;
	private JPasswordField pw;
	private JButton b1 , b2 , b3;
	private JLabel l1 , l2 , l3;
	File textfile , imagefile;
	static final int N = 50;
	Rsa1 rsa;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Decrypt frame = new Decrypt();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Decrypt() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 536, 428);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		l1 = new JLabel("Select Image");
		l1.setFont(new Font("Tahoma", Font.PLAIN, 19));
		l1.setBounds(10, 55, 116, 32);
		contentPane.add(l1);
		
		l2 = new JLabel("Select File");
		l2.setFont(new Font("Tahoma", Font.PLAIN, 19));
		l2.setBounds(10, 147, 89, 32);
		contentPane.add(l2);
		
		l3 = new JLabel("Password");
		l3.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		l3.setBounds(10, 233, 106, 32);
		contentPane.add(l3);
		
		t1 = new JTextField();
		t1.setBounds(126, 58, 292, 32);
		contentPane.add(t1);
		t1.setColumns(10);
		
		t2 = new JTextField();
		t2.setColumns(10);
		t2.setBounds(126, 147, 292, 32);
		contentPane.add(t2);
		
		pw = new JPasswordField();
		pw.setBounds(126, 239, 247, 32);
		contentPane.add(pw);
		
		b1 = new JButton("Choose");
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object ob =e.getSource();
				if(ob==b1) {
					JFileChooser file_chooser=new JFileChooser();
					int v=file_chooser.showOpenDialog(null);
					FileNameExtensionFilter filter=new FileNameExtensionFilter("JPG & GIF File","jpg","gif");
					file_chooser.setFileFilter(filter);
					if(v==JFileChooser.APPROVE_OPTION) {
						imagefile=file_chooser.getSelectedFile();
						String abspath=imagefile.getAbsolutePath();
						t1.setText(abspath);
					}
					
				}
			}
		});
		b1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		b1.setBounds(421, 58, 89, 32);
		contentPane.add(b1);
		
		b2 = new JButton("Choose");
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object ob =e.getSource();
				if(ob==b2) {
					JFileChooser file_chooser=new JFileChooser();
					int v=file_chooser.showOpenDialog(null);
					FileNameExtensionFilter filter=new FileNameExtensionFilter("Text File","txt");
					file_chooser.setFileFilter(filter);
					if(v==JFileChooser.APPROVE_OPTION) {
						textfile=file_chooser.getSelectedFile();
						String abspath=textfile.getAbsolutePath();
						t2.setText(abspath);
					}
					
				}
			}
			
		});
		b2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		b2.setBounds(421, 147, 89, 29);
		contentPane.add(b2);
		
		b3 = new JButton("DECRYPT");
		b3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object ob=e.getSource();
				if(ob==b3){
					try{
						int index=0,cnt=0;
						RandomAccessFile file=new RandomAccessFile(imagefile,"r");
						int i=0;
						while((i=file.read())!=-1){
							cnt++;
							if(i==32)
								index=cnt;
						}
						file.seek(index);
						String str="";
						while((i=file.read())!=-1){
							str=str+(char)i;
						}
						System.out.println("str: "+str);
						str=str.trim();
						
						String msg[]=str.split("#");
						String encpwd=msg[0];
						String modulus=msg[msg.length-2];
						String privatekey=msg[msg.length-1];
						
						System.out.println("Enc Password: "+encpwd);
						System.out.println("Modulus: "+modulus);
						System.out.println("Private Key: "+privatekey);
						
						BigInteger p=new BigInteger(encpwd);
						BigInteger m=new BigInteger(modulus);
						BigInteger pk=new BigInteger(privatekey);
						BigInteger pwd=p.modPow(pk,m);
						
						byte b[]=pwd.toByteArray();
						String pass=new String(b);
						System.out.println("Actual Password: "+pass);
						
						String passwd=pw.getText();
						if(pass.equals(passwd)){
							String original="";
							for(i=1;i<=msg.length-3;i++){
								BigInteger em=new BigInteger(msg[i]);
								BigInteger dm=em.modPow(pk,m);
								byte b1[]=dm.toByteArray();
								String om=new String(b1);
								original=original+om;
							}
							System.out.println("Original Message: "+original);
							FileOutputStream out=new FileOutputStream(textfile);
							out.write(original.getBytes());
							JOptionPane.showMessageDialog(null,"Message has been successfully decrypted to "+textfile.getName());
							out.close();
						}
						else
							JOptionPane.showMessageDialog(null,"Invalid Password");
						
						
						file.close();
					}
					catch(Exception ex){
						ex.printStackTrace();
					}
					
				}
			}
		});
		b3.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		b3.setBounds(126, 306, 247, 40);
		contentPane.add(b3);
	}
}
