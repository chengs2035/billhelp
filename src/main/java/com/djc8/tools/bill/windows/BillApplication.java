//package com.djc8.tools.bill.windows;
//
//import com.djc8.tools.bill.BillStart;
//import javafx.application.Application;
//import javafx.application.Platform;
//import javafx.geometry.HPos;
//import javafx.geometry.Insets;
//import javafx.geometry.Pos;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.HBox;
//import javafx.scene.paint.Color;
//import javafx.scene.text.Font;
//import javafx.scene.text.Text;
//import javafx.stage.DirectoryChooser;
//import javafx.stage.Stage;
//
//import java.beans.Encoder;
//import java.io.*;
//
//public class BillApplication extends Application {
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//
//    private TextArea console;
//    private PrintStream ps ;
//
//    @Override
//    public void start(Stage primaryStage) {
//
//
//        primaryStage.setTitle("票据脚本");
//        GridPane grid = new GridPane();
//        grid.setAlignment(Pos.CENTER);
//        grid.setHgap(10);
//        grid.setVgap(10);
//        grid.setPadding(new Insets(25, 25, 25, 25));
//
//        Label secretIdlbl = new Label("secretId");
//        grid.add(secretIdlbl, 0, 1);
//
//        TextField secretIdtxt = new TextField("AKIDUbdjVpT5UWDcLHpi46t1mukMDHTpB7Fi");
//        grid.add(secretIdtxt, 1, 1);
//
//        Label secretKeylbl = new Label("secretKey:");
//        grid.add(secretKeylbl, 0, 2);
//
//        TextField secretKeytxt = new TextField("UC8PSWMUXotuRllKZCeH4m6VdAaOfBXC");
//        grid.add(secretKeytxt, 1, 2);
//
//        Label directoryChooserlbl=new Label("目录:");
//        grid.add(directoryChooserlbl,0,3);
//
//        Text directoryTxt=new Text("");
//        grid.add(directoryTxt,1,3);
//
//        Button btnChooserDirect=new Button("...");
//        grid.add(btnChooserDirect,2,3);
//
//        Button btn = new Button("开始处理");
//        HBox hbBtn = new HBox(10);
//        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
//        hbBtn.getChildren().add(btn);
//        grid.add(hbBtn, 1, 5);
//
//
//
//        console=new TextArea();
//
//        Font.getFamilies().forEach(ss->{
//            System.out.println(ss);
//        });
//        console.setFont(new Font("WenQuanYi Zen Hei Mono",10));
//
//        grid.add(console,0,6,3,1);
//
//
//        ps = new PrintStream(new Console(console));
//        System.setOut(ps);
//        System.setErr(ps);
//
//        btn.setOnAction(e -> {
//
//            //
//            if(directoryTxt.getText().equals("") || directoryTxt.getText()==null){
//                Alert a=new Alert(Alert.AlertType.ERROR);
//                a.setContentText("请先选择目录!");
//                a.showAndWait();
//                return;
//            }
//            Runnable r= () -> BillStart.star2(directoryTxt.getText());
//            new Thread(r).start();
//
//        });
//        btnChooserDirect.setOnAction(event -> {
//
//            DirectoryChooser file=new DirectoryChooser();
//            file.setTitle("请选择目录");
//            // file.setInitialDirectory(new File(local_rootText.getText()));
//            Stage stage = (Stage) btnChooserDirect.getScene().getWindow();
//
//            File newFolder = file.showDialog(stage);//这个file就是选择的文件夹了
//            if(newFolder!=null)
//                directoryTxt.setText(newFolder.getAbsolutePath());
//            else
//                System.out.println("Please choose a direct");
//        });
//        Scene scene = new Scene(grid, 500, 500);
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//
//
//    public class Console extends OutputStream {
//        private TextArea console;
//        public Console(TextArea console){
//            this.console=console;
//        }
//        public void appendText(String valueof){
//
//            Platform.runLater(()-> {
//                try {
//                    String string_utf8=new String(valueof.getBytes("UTF-8"),"ISO8859-1");
//                    console.appendText(string_utf8);
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//            });
//
//        }
//        public void write(int b) throws IOException {
//            appendText(String.valueOf((char)b));
//        }
//    }
//
//
//}
