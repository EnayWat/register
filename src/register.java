import javax.swing.*;
import com.mongodb.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class register {
    private JPanel mainP;
    private JTextField UserName;
    private JPasswordField Password;
    private JButton register;
    private JButton cancel;
    private JRadioButton female;
    private JRadioButton male;
    private JTextField name;
    static MongoClientURI uri;
    static MongoClient mongo;
    static DB db;
    static DBCollection user;
    static String gender;
    static DBObject CneckUname;
    static DBObject Cneckname;

    public register() {
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submit();
            }
        });
        female.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setRadiol();
            }
        });
        male.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setRadiol();
            }
        });
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                resetText();
            }
        });
    }

    private void submit() {
        BasicDBObject SQ1 = new BasicDBObject();
        BasicDBObject SQ2 = new BasicDBObject();
        SQ1.put("username",UserName.getText());
        SQ2.put("name",name.getText());
        CneckUname = user.findOne(SQ1);
        Cneckname = user.findOne(SQ2);
        if(UserName.getText().isEmpty() || name.getText().isEmpty() || Password.getPassword().length == 0 ||gender == null) {
            JOptionPane.showMessageDialog(null,"ใส่ข้อมูลไม่ครบ");
        }else if(CneckUname != null){
            JOptionPane.showMessageDialog(null,"UserName ซ้ำ");
        }else if (Cneckname != null){
            JOptionPane.showMessageDialog(null,"Name ซ้ำ");
        } else {
            BasicDBObject add = new BasicDBObject();
            add.put("username", UserName.getText());
            add.put("password", new String(Password.getPassword()));
            add.put("name", name.getText());
            add.put("gender", gender);
            user.insert(add);
            JOptionPane.showMessageDialog(null, "สมัครสำเร็จ");
            resetText();
        }
    }

    private void resetText() {
        name.setText("");
        UserName.setText("");
        Password.setText("");
        female.setSelected(false);
        male.setSelected(false);
    }

    public void setRadiol(){
        if(male.isSelected()){
            female.setSelected(false);
            female.updateUI();
            gender = "male";
        }
    }
    public void setRadio2(){
        if(female.isSelected()){
            male.setSelected(false);
            male.updateUI();
            gender = "female";
        }
    }

    public static void main (String[] args) {
        JFrame frame = new JFrame();
        register regi = new register();
        frame.setContentPane(regi.mainP);
        regi.connectDB();
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    public void connectDB(){
        try{
            uri = new MongoClientURI("mongodb://admin:88634159sd@ds245532.mlab.com:45532/ox");
            mongo = new MongoClient(uri);
            db = mongo.getDB(uri.getDatabase());
            user = db.getCollection("User");
        }catch (Exception e){

        }
    }
}
