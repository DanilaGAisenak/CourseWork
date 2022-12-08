package server;

import com.mysql.cj.protocol.Resultset;

import java.io.*;
import java.net.Socket;
import java.sql.*;
public class ClientHandler implements Runnable{
    private static Socket clientSocket;
    //private static DataInputStream is;
    //private static DataOutputStream os;
    private static ObjectInputStream ois;
    private static  ObjectOutputStream oos;
    private static Connection connection;
    private static ResultSet rs;
    private static Statement stmt;

    public ClientHandler(Socket client, Connection connection,
                         ObjectOutputStream oos, ObjectInputStream ois) {
        this.clientSocket = client;
        //this.is = is;
        //this.os = os;
        this.connection = connection;
        this.oos = oos;
        this.ois = ois;
        try {
            stmt = connection.createStatement();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try{
            Integer num = 0;
            while (true/*(line=ois.readUTF())!=null*/) {
                System.out.println("Ожидание");
                String line=ois.readUTF();
                //System.out.println(line);
                num = Integer.parseInt(line);
                System.out.println(num);
                switch (num) {
                    case 1/*Вход*/: {
                       line = ois.readUTF();
                       System.out.println("Sent from client " + line);
                       String val[] = line.split(" ");
                       String query = "select count(*) from users";
                       rs = stmt.executeQuery(query);
                       int number = 0;
                       while (rs.next()) {
                           number = rs.getInt(1);
                       }
                       query = "SELECT * FROM users";
                       rs = stmt.executeQuery(query);
                       int j = 0;
                       Integer result = 0;
                       Integer[] id = new Integer[number];
                       String[] login = new String[number];
                       String[] pass = new String[number];
                       while (rs.next()) {
                           id[j] = rs.getInt(1);
                           login[j] = rs.getString(2);
                           pass[j] = rs.getString(3);
                           j++;
                       }
                       //for (int i = 0; i < login.length; i++) {
                       //    System.out.println(id[i] + " " + login[i] + " " + pass[i]);
                       //}
                       int counter = 0;
                       for (int i = 0; i < login.length; i++) {
                           if (login[i].equals(val[0])) {
                               if (pass[i].equals(val[1])) {
                                   result = 1;
                                   System.out.println(result.toString());
                                   oos.writeUTF(result.toString());
                                   oos.flush();
                                   if (id[i] == 1) {
                                       oos.writeUTF("A");
                                       oos.flush();
                                       i = login.length;
                                       break;
                                   } else {
                                       oos.writeUTF("U");
                                       oos.flush();
                                       oos.writeUTF(id[i].toString());
                                       oos.flush();
                                       i = login.length;
                                   }
                               }
                           } else {
                               counter++;
                               //System.out.println(result.toString());
                               //os.writeUTF("0");
                               //os.flush();
                           }
                           if (counter == login.length - 1) {
                               oos.writeUTF(result.toString());
                               oos.flush();
                           }
                       }
                        System.out.println("Конец 1");
                    }break;
                    case 2:/*Регистрация*/ {
                        while((line= ois.readUTF())!=null) {
                            System.out.println("Sent from client " + line);
                            String val[] = line.split(" ");
                            String query = "INSERT Users(Логин, Пароль) VALUES (?,?)";
                            PreparedStatement statement = connection.prepareStatement(query);
                            statement.setString(1, val[0]);
                            statement.setString(2, val[1]);
                            statement.execute();
                            oos.writeUTF("Command proceeded");
                            oos.flush();
                            break;
                        }
                        System.out.println("Конец 2");
                    }break;
                    case 3/*Данные в таблицу*/:{
                        String query = "select count(*) from users";
                        rs = stmt.executeQuery(query);
                        Integer number = 0;
                        while (rs.next()) {
                            number = rs.getInt(1);
                        }
                        oos.writeObject(number);
                        oos.flush();
                        query = "select * from users";
                        rs = stmt.executeQuery(query);
                        User user = new User(rs);
                        oos.writeObject((User)user);
                        System.out.println("Конец 3");
                    }break;
                    case 4/*Удаление пользователя*/: {
                        line = ois.readUTF();
                        String[] val = line.split(" ");
                        String query = "DELETE FROM users WHERE idПользователя="+val[0];
                        stmt.executeUpdate(query);
                        oos.writeUTF("Command proceeded");
                        oos.flush();
                        System.out.println("Конец 4");
                    }break;
                    case 5/*Изменение пользователя*/: {
                        line = ois.readUTF();
                        String[] val = line.split(" ");
                        PreparedStatement prep = connection.prepareStatement("UPDATE users SET Логин=?, Пароль=? WHERE idПользователя=?");
                        prep.setString(1,val[1]);
                        prep.setString(2,val[2]);
                        prep.setString(3,val[0]);
                        prep.execute();
                        oos.writeUTF("Command proceeded");
                        oos.flush();
                        System.out.println("Конец 5");
                    }break;
                    case 6/*Заполнение таблицы ПО*/:{
                        String query = "select count(*) from software";
                        rs = stmt.executeQuery(query);
                        Integer number = 0;
                        while (rs.next()) {
                            number = rs.getInt(1);
                        }
                        oos.writeObject(number);
                        oos.flush();
                        query = "select * from software";
                        rs = stmt.executeQuery(query);
                        Software sw = new Software(rs);
                        oos.writeObject((Software)sw);
                        System.out.println("Конец 6");
                    }break;
                    case 7/*Добавление ПО*/:{
                        line = ois.readUTF();
                        System.out.println("Sent from client " + line);
                        String[] val = line.split(" ");
                        String query = "INSERT Software(Название_ПО, Цена_за_год, Производитель_ПО) VALUES(?,?,?)";
                        PreparedStatement statement = connection.prepareStatement(query);
                        statement.setString(1, val[0]);
                        statement.setString(2, val[1]);
                        statement.setString(3,val[2]);
                        statement.execute();
                        oos.writeUTF("Command proceeded");
                        oos.flush();
                        System.out.println("Конец 7");
                    }break;
                    case 8/*Удаление По*/:{
                        line = ois.readUTF();
                        String[] val = line.split(" ");
                        String query = "DELETE FROM software WHERE id_ПО="+val[0];
                        stmt.executeUpdate(query);
                        oos.writeUTF("Command proceeded");
                        oos.flush();
                        System.out.println("Конец 8");
                    }break;
                    case 9/*Редактирование ПО*/:{
                        line = ois.readUTF();
                        String[] val = line.split(" ");
                        PreparedStatement prep = connection.prepareStatement("UPDATE software SET Название_ПО=?, Цена_за_год=?, Производитель_ПО=? WHERE id_ПО=?");
                        prep.setString(1,val[0]);
                        prep.setString(2,val[1]);
                        prep.setString(3,val[2]);
                        prep.setString(4,val[3]);
                        prep.execute();
                        oos.writeUTF("Command proceeded");
                        oos.flush();
                        System.out.println("Конец 9");
                    }break;
                    case 10/*Заполнение таблицы AО*/:{
                        String query = "select count(*) from hardware";
                        rs = stmt.executeQuery(query);
                        Integer number = 0;
                        while (rs.next()) {
                            number = rs.getInt(1);
                        }
                        oos.writeObject(number);
                        oos.flush();
                        query = "select * from hardware";
                        rs = stmt.executeQuery(query);
                        Hardware hw = new Hardware(rs);
                        oos.writeObject((Hardware)hw);
                        System.out.println("Конец 10");
                    }break;
                    case 11/*Добавление AО*/:{
                        line = ois.readUTF();
                        System.out.println("Sent from client " + line);
                        String[] val = line.split(" ");
                        String query = "INSERT Hardware(Название_АО, Цена_АО, Производитель_АО) VALUES(?,?,?)";
                        PreparedStatement statement = connection.prepareStatement(query);
                        statement.setString(1, val[0]);
                        statement.setString(2, val[1]);
                        statement.setString(3,val[2]);
                        statement.execute();
                        oos.writeUTF("Command proceeded");
                        oos.flush();
                        System.out.println("Конец 11");
                    }
                    case 12/*Редактирование AО*/:{
                        line = ois.readUTF();
                        String[] val = line.split(" ");
                        PreparedStatement prep = connection.prepareStatement("UPDATE hardware SET Название_АО=?, Цена_АО=?, Производитель_АО=? WHERE id_АО=?");
                        prep.setString(1,val[0]);
                        prep.setString(2,val[1]);
                        prep.setString(3,val[2]);
                        prep.setString(4,val[3]);
                        prep.execute();
                        oos.writeUTF("Command proceeded");
                        oos.flush();
                        System.out.println("Конец 12");
                    }
                    case 13/*Удаление AO*/: {
                        line = ois.readUTF();
                        String[] val = line.split(" ");
                        String query = "DELETE FROM hardware WHERE id_АО="+val[0];
                        stmt.executeUpdate(query);
                        oos.writeUTF("Command proceeded");
                        oos.flush();
                        System.out.println("Конец 13");
                    }
                    case 14/*Данные в таблицы Пользователя*/: {
                        //license
                        String query = "select count(*) from licenses";
                        rs = stmt.executeQuery(query);
                        Integer number = 0;
                        while (rs.next()) {
                            number = rs.getInt(1);
                        }
                        oos.writeObject(number);
                        oos.flush();
                        query = "select * from licenses";
                        rs = stmt.executeQuery(query);
                        License license = new License(rs);
                        oos.writeObject((License)license);
                        //hwstatus
                        query = "select count(*) from hwstatus";
                        rs = stmt.executeQuery(query);
                        number = 0;
                        while (rs.next()) {
                            number = rs.getInt(1);
                        }
                        oos.writeObject(number);
                        oos.flush();
                        query = "select * from hwstatus";
                        rs = stmt.executeQuery(query);
                        Hws hws = new Hws(rs);
                        oos.writeObject((Hws)hws);
                        //company
                        query = "select count(*) from company";
                        rs = stmt.executeQuery(query);
                        number = 0;
                        while (rs.next()) {
                            number = rs.getInt(1);
                        }
                        oos.writeObject(number);
                        oos.flush();
                        query = "select * from company";
                        rs = stmt.executeQuery(query);
                        Company com = new Company(rs);
                        oos.writeObject((Company)com);
                        System.out.println("Конец 14");
                    }break;
                    case 15/*Добавление компании*/: {
                        line = ois.readUTF();
                        System.out.println("Sent from client " + line);
                        String[] val = line.split(" ");
                        String query = "INSERT Company(Название_Компании, Количество_рабочих_станций, id_Пользователя) VALUES(?,?,?)";
                        PreparedStatement statement = connection.prepareStatement(query);
                        statement.setString(1, val[0]);
                        statement.setString(2, val[1]);
                        statement.setString(3,val[2]);
                        statement.execute();
                        oos.writeUTF("Command proceeded");
                        oos.flush();
                        System.out.println("Конец 15");
                    }break;
                    case 16/*Добавление лицензии*/:{
                        line = ois.readUTF();
                        System.out.println("Sent from client " + line);
                        String[] val = line.split(" ");
                        String query = "INSERT Licenses(id_Компании, id_По, Истечение_Срока) VALUES(?,?,?)";
                        PreparedStatement statement = connection.prepareStatement(query);
                        statement.setString(1, val[0]);
                        statement.setString(2, val[1]);
                        statement.setString(3,val[2]);
                        statement.execute();
                        oos.writeUTF("Command proceeded");
                        oos.flush();
                        System.out.println("Конец 16");
                    }break;
                    case 17/*Добавление лицензии АО*/:{
                        line = ois.readUTF();
                        System.out.println("Sent from client " + line);
                        String[] val = line.split(" ");
                        String query = "INSERT Hwstatus(id_Компании, id_АО, Дата_Покупки_АО, Дата_Окончания_Срока_Эксплуатации, Количество) VALUES(?,?,?,?,?)";
                        PreparedStatement statement = connection.prepareStatement(query);
                        statement.setString(1, val[0]);
                        statement.setString(2, val[1]);
                        statement.setString(3,val[2]);
                        statement.setString(4,val[3]);
                        statement.setString(5,val[4]);
                        statement.execute();
                        oos.writeUTF("Command proceeded");
                        oos.flush();
                        System.out.println("Конец 17");
                    }break;
                    case 18/*Продление лицензии*/:{
                        line = ois.readUTF();
                        String[] val = line.split(" ");
                        PreparedStatement prep = connection.prepareStatement("UPDATE licenses SET Истечение_Срока= DATE_ADD(Истечение_Срока, INTERVAL ? YEAR) WHERE id_Лицензии=?");
                        prep.setString(1,val[0]);
                        prep.setString(2,val[1]);
                        prep.execute();
                        oos.writeUTF("Command proceeded");
                        oos.flush();
                        System.out.println("Конец 18");
                    }break;
                    case 19/*Удаление лицензии*/:{
                        line = ois.readUTF();
                        String[] val = line.split(" ");
                        String query = "DELETE FROM licenses WHERE id_Лицензии="+val[0];
                        stmt.executeUpdate(query);
                        oos.writeUTF("Command proceeded");
                        oos.flush();
                        System.out.println("Конец 19");
                    }break;
                    case 20/*Редактирование лицензии АО*/:{
                        line = ois.readUTF();
                        String[] val = line.split(" ");
                        PreparedStatement prep = connection.prepareStatement("UPDATE hwstatus SET id_Компании=?, id_АО=?, Дата_Покупки_АО=?, Дата_Окончания_Срока_Эксплуатации=?,Количество=? WHERE id=?");
                        prep.setString(1,val[0]);
                        prep.setString(2,val[1]);
                        prep.setString(3,val[2]);
                        prep.setString(4,val[3]);
                        prep.setString(5,val[4]);
                        prep.setString(6,val[5]);
                        prep.execute();
                        oos.writeUTF("Command proceeded");
                        oos.flush();
                        System.out.println("Конец 20");
                    }
                    case 21/*Удаление лицензии АО*/:{
                        line = ois.readUTF();
                        //String[] val = line.split(" ");
                        String query = "DELETE FROM hwstatus WHERE id="+line;
                        stmt.executeUpdate(query);
                        oos.writeUTF("Command proceeded");
                        oos.flush();
                        System.out.println("Конец 21");
                    }break;
                    case 22/*Редактирование компании*/:{
                        line = ois.readUTF();
                        String[] val = line.split(" ");
                        PreparedStatement prep = connection.prepareStatement("UPDATE company SET Название_Компании=?, Количество_рабочих_станций=? WHERE idКомпании=?");
                        prep.setString(1,val[0]);
                        prep.setString(2,val[1]);
                        prep.setString(3,val[2]);
                        prep.execute();
                        oos.writeUTF("Command proceeded");
                        oos.flush();
                        System.out.println("Конец 22");
                    }break;
                    case 23/*Удаление компании*/:{
                        line = ois.readUTF();
                        String[] val = line.split(" ");
                        String query = "DELETE FROM hwstatus WHERE id_Компании="+val[2];
                        stmt.executeUpdate(query);
                        query = "DELETE FROM licenses WHERE id_Компании="+val[2];
                        stmt.executeUpdate(query);
                        query = "DELETE FROM company WHERE idКомпании="+val[2];
                        stmt.executeUpdate(query);
                        oos.writeUTF("Command proceeded");
                        oos.flush();
                        System.out.println("Конец 23");
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try{
                stmt.close();
                if (oos != null){
                    oos.close();
                }
                if (ois != null){
                    ois.close();
                    clientSocket.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
