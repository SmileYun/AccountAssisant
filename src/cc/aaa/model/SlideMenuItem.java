package cc.aaa.model;

public class SlideMenuItem {
	private int mItemID;
	private String mTitle;
	
	public SlideMenuItem(int pItemID,String pTitle)
	{
		mItemID = pItemID;
		mTitle = pTitle;
	}

	public int getmItemID() {
		return mItemID;
	}

	public void setmItemID(int mItemID) {
		this.mItemID = mItemID;
	}

	public String getmTitle() {
		return mTitle;
	}

	public void setmTitle(String mTitle) {
		this.mTitle = mTitle;
	}
}
