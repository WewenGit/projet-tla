import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Main extends JFrame {
	private static JTextArea textArea;
	private static JScrollPane scrollPane;
	private static int nextScene=0;
	private static boolean changeOccured=false;

	public Main() {

		setSize(500,500);
		setTitle("Projet TLA");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new FlowLayout());

		WindowListener l = new WindowAdapter() {
			public void windowClosing(WindowEvent e){
			   System.exit(0);
			}
		 };
		 
		addWindowListener(l);

        textArea = new JTextArea();
        textArea.setFont(new Font("SansSerif", Font.PLAIN, 12));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        int preferredWidth = (int) (getSize().width * 0.7);
        int preferredHeight = (int) (getSize().height * 0.7);
        textArea.setPreferredSize(new Dimension(preferredWidth, preferredHeight));

        scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
        getContentPane().add(scrollPane);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustTextAreaSize();
            }
        });

    	setLocationRelativeTo(null);
        setVisible(true);

	}
	 
	public static JTextArea getTextArea(){
		return textArea;
	}

	public static JScrollPane getScrollPane(){
		return scrollPane;
	}

	private void adjustTextAreaSize(){
        int newWidth = (int) (getWidth() * 0.7);
        int newHeight = (int) (getHeight() * 0.7);

        textArea.setPreferredSize(new Dimension(newWidth, newHeight));

		textArea.revalidate();
        scrollPane.revalidate();
	}


	private static void run(List<Token> lt) {
		//WINDOW DEFINITION
		JFrame window = new Main();

		//GAME
		AnalyseSyntaxique ansyn = new AnalyseSyntaxique();
		Game game = ansyn.analyse(lt);
		boolean inGame=true;
		Scene s = game.getScenes().get(0);
		String sceneText=s.getText();
		List<JButton> buttonList = new ArrayList<>();
		getTextArea().setText(sceneText);
		for (Choice choice : game.getChoices()) {
			if (choice.getRelation()==s.getId()) {
				JButton button = new JButton(choice.getText());
        
				button.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						nextScene=choice.getNextScene();
						changeOccured=true;
						for (JButton jButton : buttonList) {
							window.getContentPane().remove(jButton);
						}
						window.revalidate();
						window.repaint();
					}
       			});

        		window.getContentPane().add(button);
				buttonList.add(button);
			}
		}

		while (inGame) {
			if (changeOccured) {
				for (Scene scene : game.getScenes()) {
					if (scene.getId()==nextScene) {
						s=scene;
						sceneText=s.getText();
						getTextArea().setText(sceneText);
						break;
					}
				}
				for (Choice choice : game.getChoices()) {
					if (choice.getRelation()==s.getId()) {
						JButton button = new JButton(choice.getText());
				
						button.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								nextScene=choice.getNextScene();
								changeOccured=true;
								for (JButton jButton : buttonList) {
									window.getContentPane().remove(jButton);
								}
								window.revalidate();
								window.repaint();
							}
						});

						window.getContentPane().add(button);
						buttonList.add(button);
					}
				}
				changeOccured=false;
			}

			//REFRESH WINDOW
			window.revalidate();
			window.repaint();
			if (s.isEnd()) {
				JButton endButton = new JButton("Sortir du jeu");
				endButton.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								window.dispose();
							}
				});
				inGame=false;
				window.getContentPane().add(endButton);
				window.revalidate();
				window.repaint();
			}
		}
	}

	public static void main(String[] args) throws IllegalCharacterException, FileNotFoundException {
		AnalyseLexicale al = new AnalyseLexicale();
		File textFile = new File("adventures/mini_aventure.txt");
		Scanner sc = new Scanner(textFile);
		String txt = "";
		while (sc.hasNextLine()) {
			txt+=sc.nextLine();
			txt+="\n";
		}
		sc.close();
		List<Token> lt = al.analyse(txt);
		/*for (Token token : lt) {
			System.out.println(token);
		}*/
		run(lt);
	}
}