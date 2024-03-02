package j6_Practise1;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
public class DongHo extends JFrame implements Runnable {

	private JLabel lb_dongHo;
	private JTextField tf_Nhap;
	private JButton bt_Open;
	public DongHo() {
		this.setTitle("Đồng hồ");
		this.setSize(300,300);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel pn = new JPanel();
		pn.setLayout(new BorderLayout());
		lb_dongHo = new JLabel("Đồng hồ" , JLabel.CENTER);
		JPanel pn_South = new JPanel();
		pn_South.setLayout(new GridLayout(1,2));
		tf_Nhap = new JTextField(30);
		tf_Nhap.setToolTipText("Nhap mui gio, vd: +07:00");
		bt_Open = new JButton("Open");
		lb_dongHo.setFont(new Font(lb_dongHo.getFont().getName(), Font.PLAIN,40));
		lb_dongHo.setForeground(Color.red);
		pn_South.add(tf_Nhap);
		pn_South.add(bt_Open);
		
		pn.add(lb_dongHo , BorderLayout.CENTER);
		pn.add(pn_South, BorderLayout.SOUTH);
		this.add(pn);
		bt_Open.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				addClock();
				new DongHo();
			}
		});
		this.setVisible(true);
	}
	
	
	
	private void addClock() {
        String offsetText = tf_Nhap.getText().trim();
        if (offsetText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a timezone offset.");
            return;
        }

        ZoneOffset offset = ZoneOffset.of(offsetText);
        ClockUpdater updater = new ClockUpdater(offset, lb_dongHo);
        Thread t = new Thread(updater);
        t.start();
    }

    private static class ClockUpdater implements Runnable {
        private ZoneOffset offset;
        private JLabel lb_DongHo;

        public ClockUpdater(ZoneOffset offset, JLabel lb_DongHo) {
            this.offset = offset;
            this.lb_DongHo = lb_DongHo;
        }
   
		public void run() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(" HH:mm:ss");
            while (true) {
                OffsetDateTime now = OffsetDateTime.now(offset);
             
         
                String formattedTime = now.format(formatter);
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                    	lb_DongHo.setText(formattedTime);
                    }
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	public static void main(String[] args) {
		new DongHo();
	}

}

