import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class RegistrationGui {
   static int i = 0;
   static int flag = 0;
   static Connection con;
   static ResultSet rs;


    public static void main(String[] args) {
        JFrame frame = new JFrame("Registeration Form");
        frame.setSize(600,500);
        frame.setLayout(null);

        JLabel label1 = new JLabel("Email :");
        label1.setBounds(10,30,80,40);
        frame.add(label1);

        JTextField tf1 = new JTextField();
        tf1.setBounds(85,30,300,40);
        frame.add(tf1);

        JLabel label2 = new JLabel("UserName :");
        label2.setBounds(10,80,80,40);
        frame.add(label2);

        JTextField tf2 = new JTextField();
        tf2.setBounds(85,80,300,40);
        frame.add(tf2);

        JButton btn = new JButton("Submit");
        btn.setBounds(285,130,100,30);
        frame.add(btn);

        JLabel label3 = new JLabel();
        label3.setBounds(80,150,250,40);
        label3.setForeground(Color.RED);
        frame.add(label3);

        JPanel panel = new JPanel();
        panel.setBounds(10,200,560,250);
        panel.setBackground(Color.CYAN);
        frame.add(panel);
        String column[]={"EMPID","EMAIL","USERNAME","ACTIONS"};
        DefaultTableModel dtm = new DefaultTableModel(column,0);
        JTable table = new JTable(dtm);
         panel.add(new JScrollPane(table));


        try {
            //Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/chena", "root", "1997");
                System.out.println("Connection Established");
                Statement st = con.createStatement();
                st.executeUpdate("create table if not exists Data(id INTEGER AUTO_INCREMENT PRIMARY KEY,Email VARCHAR(255),Username VARCHAR(255),Actions VARCHAR(255))");
                System.out.println("Table created successfully");


            rs=st.executeQuery("select * from data");
            while(rs.next()) {
                int srno = rs.getInt("id");
                String srn = Integer.toString(srno);

                String email = rs.getString("Email");
                String username = rs.getString("Username");
                String action = rs.getString("Actions");
                String r[]={srn,email,username,action};
                System.out.println(email);
                dtm.addRow(r);
            }


        }
        catch (Exception ee)
        {
            System.out.println(ee);
        }




        btn.addActionListener(e -> {

        try {

            String sql ="INSERT INTO Data(Email,Username,Actions) VALUES("+"'"+tf1.getText()+"','"+tf2.getText()+"','2005')";
         //  PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            Statement st = con.createStatement();
            st.executeUpdate(sql);
/*
            ps.getGeneratedKeys();
            String mail = tf1.getText();
            String wrong = "Email is Inconrrect ...Please Try Again";

            if(mail.contains("@")&&mail.contains(".")) {
               ps.setString(1, tf1.getText());
                ps.setString(2, tf2.getText());
                ps.setString(3, "2005");
                ps.executeUpdate();
                System.out.println("Data added successfully");
                label3.setText("");
            }
            else {
               label3.setText(wrong);

           }
*/

                 rs = st.executeQuery("select * from data where id = (select max(id) from data)");
            while(rs.next()) {
                int srno = rs.getInt("id");
                String srn = Integer.toString(srno);

                String email = rs.getString("Email");
                String username = rs.getString("Username");
                String action = rs.getString("Actions");
                String r[]={srn,email,username,action};
                dtm.addRow(r);
            }


        } catch (Exception f)
        {
            System.out.println(f);
        }

        tf1.setText("");
        tf2.setText("");
        });

        frame.setVisible(true);
    }
}
