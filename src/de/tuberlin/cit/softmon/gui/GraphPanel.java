package de.tuberlin.cit.softmon.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class GraphPanel extends JPanel {
	
	public static final String CARD_NO_SELECTION = "noSelectionCard";
	public static final String CARD_SWITCH = "switchCard";
	public static final String CARD_PORT = "portCard";
	public static final String CARD_FLOW = "flowCard";
	
	private JPanel m_graphCards;

	private SwitchCard m_switchCard;
	private PortCard m_portCard;
	private FlowCard m_flowCard;
	
	
	public GraphPanel() {
		setLayout(new BorderLayout(0, 0));
		
		// graph cards panel
		m_graphCards = new JPanel();
		add(m_graphCards, BorderLayout.CENTER);
		m_graphCards.setLayout(new CardLayout(0, 0));
		
		// no selection card
		JPanel noSelectionCard = new JPanel();
		noSelectionCard.setBorder(new CompoundBorder(new EmptyBorder(6, 2, 2, 2), 
				new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), 
						"", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0))));
		
		m_graphCards.add(noSelectionCard, CARD_NO_SELECTION);
		noSelectionCard.setLayout(new BorderLayout(0, 0));
		
		JLabel lblnNoSelection = new JLabel("(No device selected.)");
		lblnNoSelection.setHorizontalAlignment(SwingConstants.CENTER);
		lblnNoSelection.setAlignmentY(Component.CENTER_ALIGNMENT);
		lblnNoSelection.setAlignmentX(Component.CENTER_ALIGNMENT);
		noSelectionCard.add(lblnNoSelection, BorderLayout.CENTER);
		
		// switch card
		m_switchCard = new SwitchCard();
		m_graphCards.add(m_switchCard, CARD_SWITCH);
		
		
		// port card
		m_portCard = new PortCard();
		m_graphCards.add(m_portCard, CARD_PORT);
		

		// flow card
		m_flowCard = new FlowCard();
		m_graphCards.add(m_flowCard, CARD_FLOW);
		
		// dev
		//showPanel(CARD_FLOW);
	}

	public void showPanel(String graphPanelName) {
		CardLayout layout = (CardLayout) m_graphCards.getLayout();
		layout.show(m_graphCards, graphPanelName);
	}
	
	public SwitchCard getSwitchCard() {
		return m_switchCard;
	}

	public PortCard getPortCard() {
		return m_portCard;
	}
	
	public FlowCard getFlowCard() {
		return m_flowCard;
	}

}
