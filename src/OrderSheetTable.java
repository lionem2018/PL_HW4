import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class OrderSheetTable implements Runnable
{
	private ArrayList<OrderSheet> orderList;
	
	private OrderSheet tmpOrder;
	private int managingMode;
	
	public OrderSheetTable()
	{
		orderList = new ArrayList<>();
	}
	
	private int getSize()
	{
		return orderList.size();
	}
	
	private void addOrderSheet()
	{
		orderList.add(tmpOrder);
		
		JOptionPane.showMessageDialog(null, "주문이 완료되었습니다.", "주문 성공", JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void removeOrderSheet()
	{
		int index;
		
		for(index = 0; index < getSize(); index++)
		{
			if(orderList.get(index).equals(tmpOrder))
				break;
		}
		
		try
		{
			if(index >= getSize())
					throw new NotExistDataException("삭제할 주문 정보가 존재하지 않습니다.");
			else
				orderList.remove(index);
			
			JOptionPane.showMessageDialog(null, "주문이 취소되었습니다.", "취소 성공", JOptionPane.INFORMATION_MESSAGE);
		}
		catch(NotExistDataException e)
		{
			
		}
	}
	
	public void setOrderInfo(String date, String customNum, int menu)
	throws IllegalInputFormException, WrongCharactersException
	{
		String form;
		Pattern pattern;
		Matcher matcher;
		
		form = "[^0-9|/]";
		pattern = Pattern.compile(form);
		matcher = pattern.matcher(date);
			
		if(matcher.find())
			throw new IllegalInputFormException("잘못된 날짜 형식입니다.");
		
		form = "[^ㄱ-ㅎ가-힣a-zA-Z0-9]";
		pattern = Pattern.compile(form);
		matcher = pattern.matcher(customNum);
		
		if(matcher.find())
			throw new WrongCharactersException("특수문자가 입력되었습니다.");
		
		if(customNum.equals(""))
			tmpOrder = new OrderSheet(date, "GUEST", menu);
		else
			tmpOrder = new OrderSheet(date, customNum, menu);
	}
	
	public void setManagingMode(int mode)
	{
		managingMode = mode;
	}
	
	private boolean isWin()
	{
		int cnt = 0;
		
		for(int i = 0; i < getSize(); i++)
		{
			if(orderList.get(i).getCustomNum().equals(tmpOrder.getCustomNum()))
				cnt++;
		}
		
		if(cnt%3 == 0)
			return true;
		else
			return false;
	}
	
	private void giveCoupon()
	{
		String output;
		if(isWin())
		{
			output = tmpOrder.getCustomNum() + "번 고객님\n" + "무료쿠폰이 배송되었습니다.";
			JOptionPane.showMessageDialog(null, output, "쿠폰 지급", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	@Override
	public void run()
	{
		// TODO Auto-generated method stub
		switch(managingMode)
		{
			case 1:
				addOrderSheet();
				giveCoupon();
				break;
			case 2:
				removeOrderSheet();
				break;
		}
		
	}
}





class OrderSheet
{
	private String date;
	private String customNum;
	private int menu;
	
	public OrderSheet(String date, String customNum, int menu)
	{
		this.date = date;
		this.customNum = customNum;
		this.menu = menu;
	}
	
	public String getDate()
	{
		return date;
	}
	
	public String getCustomNum()
	{
		return customNum;
	}
	
	public int getMenu()
	{
		return menu;
	}
	
	public boolean equals(OrderSheet input)
	{
		if(date.equals(input.getDate()) && customNum.equals(input.getCustomNum()) && menu == input.getMenu())
		{
			return true;
		}
		
		return false;
	}
}