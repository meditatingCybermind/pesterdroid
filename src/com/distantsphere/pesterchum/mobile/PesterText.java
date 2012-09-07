package com.distantsphere.pesterchum.mobile;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;
import java.util.Vector;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.text.util.Linkify;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

public class PesterText extends EditText {
	private final Pattern _ctag_begin   = Pattern.compile("(?i)<c=(.*?)>");
	private final Pattern _ctag_end     = Pattern.compile("(?i)</c>");
	private final Pattern _ctag_rgb     = Pattern.compile("\\d+,\\d+,\\d+");
	
	private final Pattern _url          = Pattern.compile("(?i)(?:^|(?<=\\s))(?:(?:https?|ftp)://|magnet:)[^\\s]+");
	private final Pattern _url2         = Pattern.compile("(?i)www\\.[^\\s]+");
	
	private final Pattern _memo         = Pattern.compile("(?:\\s|^)(#[A-Za-z0-9_]+)");
	private final Pattern _handle       = Pattern.compile("(?:\\s|^)(@[A-Za-z0-9_]+)");
	
	//private final Pattern _img          = Pattern.compile("(?i):(\\S+):");
	private final Pattern _img2         = Pattern.compile("(?i)<img src=[\'\"](\\S+)[\'\"]\\s*/>");
	
	private final Pattern _me_cmd       = Pattern.compile("^(?:/me|PESTERCHUM:ME) ?(\\S*)");
	private final Pattern __ooc         = Pattern.compile("[\\[(][\\[(].*[\\])][\\])]");
	
	private final Pattern _format_begin = Pattern.compile("(?i)<([ibu])>");
	private final Pattern _format_end   = Pattern.compile("(?i)</([ibu])>");
	
	private static final HashMap<String, Integer> smiledict = new HashMap<String, Integer>();
	static {
		smiledict.put(":rancorous:",        R.drawable.rancorous);
		smiledict.put(":apple:",            R.drawable.chummy);
		smiledict.put(":bathearst:",        R.drawable.chummy);
		smiledict.put(":cathearst:",        R.drawable.chummy);
		smiledict.put(":woeful:",           R.drawable.bemused);
		smiledict.put(":sorrow:",           R.drawable.chummy);
		smiledict.put(":pleasant:",         R.drawable.pleasant);
		smiledict.put(":blueghost:",        R.drawable.chummy);
		smiledict.put(":slimer:",           R.drawable.chummy);
		smiledict.put(":candycorn:",        R.drawable.chummy);
		smiledict.put(":cheer:",            R.drawable.chummy);
		smiledict.put(":duhjohn:",          R.drawable.chummy);
		smiledict.put(":datrump:",          R.drawable.chummy);
		smiledict.put(":facepalm:",         R.drawable.chummy);
		smiledict.put(":bonk:",             R.drawable.chummy);
		smiledict.put(":mspa:",             R.drawable.chummy);
		smiledict.put(":gun:",              R.drawable.chummy);
		smiledict.put(":cal:",              R.drawable.chummy);
		smiledict.put(":amazedfirman:",     R.drawable.chummy);
		smiledict.put(":amazed:",           R.drawable.amazed);
		smiledict.put(":chummy:",           R.drawable.chummy);
		smiledict.put(":cool:",             R.drawable.smooth);
		smiledict.put(":smooth:",           R.drawable.smooth);
		smiledict.put(":distraughtfirman",  R.drawable.chummy);
		smiledict.put(":distraught:",       R.drawable.distraught);
		smiledict.put(":insolent:",         R.drawable.insolent);
		smiledict.put(":bemused:",          R.drawable.bemused);
		smiledict.put(":3:",                R.drawable.chummy);
		smiledict.put(":mystified:",        R.drawable.mystified);
		smiledict.put(":pranky:",           R.drawable.pranky);
		smiledict.put(":tense:",            R.drawable.bemused);
		smiledict.put(":record:",           R.drawable.chummy);
		smiledict.put(":squiddle:",         R.drawable.chummy);
		smiledict.put(":tab:",              R.drawable.chummy);
		smiledict.put(":beetip:",           R.drawable.chummy);
		smiledict.put(":flipout:",          R.drawable.chummy);
		smiledict.put(":befuddled:",        R.drawable.chummy);
		smiledict.put(":pumpkin:",          R.drawable.chummy);
		smiledict.put(":trollcool:",        R.drawable.chummy);
		smiledict.put(":jadecry:",          R.drawable.chummy);
		smiledict.put(":ecstatic:",         R.drawable.ecstatic);
		smiledict.put(":relaxed:",          R.drawable.relaxed);
		smiledict.put(":discontent:",       R.drawable.discontent);
		smiledict.put(":devious:",          R.drawable.devious);
		smiledict.put(":sleek:",            R.drawable.sleek);
		smiledict.put(":detestful:",        R.drawable.detestful);
		smiledict.put(":mirthful:",         R.drawable.mirthful);
		smiledict.put(":manipulative:",     R.drawable.manipulative);
		smiledict.put(":vigorous:",         R.drawable.vigorous);
		smiledict.put(":perky:",            R.drawable.perky);
		smiledict.put(":acceptant:",        R.drawable.acceptant);
		smiledict.put(":olliesouty:",       R.drawable.chummy);
		smiledict.put(":billiards:",        R.drawable.chummy);
		smiledict.put(":billiardslarge:",   R.drawable.chummy);
		smiledict.put(":whatdidyoudo:",     R.drawable.chummy);
		smiledict.put(":brocool:",          R.drawable.chummy);
		smiledict.put(":trollbro:",         R.drawable.chummy);
		smiledict.put(":playagame:",        R.drawable.chummy);
		smiledict.put(":trollc00l:",        R.drawable.chummy);
		smiledict.put(":suckers:",          R.drawable.chummy);
		smiledict.put(":scorpio:",          R.drawable.chummy);
		smiledict.put(":shades:",           R.drawable.chummy);
	}
	
	private final Pattern _smile = Pattern.compile(join(smiledict.keySet(), "|"));
	
	Pattern[] lexlist = { _me_cmd, _ctag_begin, _ctag_end, _format_begin, _format_end, _img2, _url, _url2, _memo, _handle, _smile };
	
	static String join(Collection<?> s, String delimiter) {
		StringBuilder builder = new StringBuilder();
		Iterator iter = s.iterator();
		while (iter.hasNext()) {
			builder.append(iter.next());
			if (!iter.hasNext()) {
				break;
			}
			builder.append(delimiter);
		}
		return builder.toString();
	}

	public PesterText(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public PesterText(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	private class CTag {
		public int mPosition;
		public String mColour;
		
		public CTag(int pos, String colour) {
			mPosition = pos;
			mColour = colour;
		}
	}
	
	private class CSpan {
		public int mStart;
		public int mEnd;
		public String mColour;
		
		public CSpan(int start, int end, String colour) {
			mStart = start;
			mEnd = end;
			mColour = colour;
		}
	}
	
	public class Style {
		public int mStart;
		public char mStyle;
		
		public Style(int start, char style) {
			mStart = start;
			mStyle = style;
		}
		
		public boolean equals(char c) {
			return (mStyle == c);
		}
	}
	
	public class StySpan {
		public int mStart;
		public int mEnd;
		public char mStyle;
		
		public StySpan(int start, int end, char style) {
			mStart = start;
			mEnd = end;
			mStyle = style;
		}
	}
	
	private enum Tag {
		NONE,
		ME_CMD,
		CTAG_BEGIN,
		CTAG_END,
		FORMAT_BEGIN,
		FORMAT_END,
		IMG,
		URL,
		URL2,
		MEMO,
		HANDLE,
		SMILEY
	}
	
	private class Span {
		//private final int mPos;
		private final String mString;
		private final MatchResult mMatch;
		private final Tag mType;
		
		public Span(Tag type, String string, MatchResult match) {
			//mPos = position;
			mType = type;
			mString = string;
			mMatch = match;
		}
		
		/*public int getPosition() {
			return mPos;
		}*/
		
		public Tag getType() {
			return mType;
		}
		
		public String getString() {
			return mString;
		}
		
		MatchResult getMatch() {
			return mMatch;
		}
	}
	
	private static final Tag[] tagValues = Tag.values();
	
	private Tag patternToTagType(Pattern re) {
		int index = 0;
		for (Pattern p : lexlist) {
			if (p.equals(re)) {
				return tagValues[index+1];
			}
			++index;
		}
		return Tag.NONE;
	}
	
	/* List of (Span)
	 * 
	 * 
	 */
	private Vector<Span> lexMessage(String text) {
		Vector<Span> stringlist = new Vector<Span>();
		stringlist.add(new Span(Tag.NONE, text, null));
		
		for (Pattern re : lexlist) {
			if (re == _url || re == _url2)
				continue;
			Log.d("regexp", re.pattern());
			Vector<Span> newstringlist = new Vector<Span>();
			for (Span s : stringlist) {
				Log.d("lex", s.getString());
				MatchResult aftermatch = null;
				if (s.getMatch() != null) {
					if (s.getString() == "") {
						newstringlist.add(s);
						continue;
					} else {
						aftermatch = s.getMatch();
					}
				}
				int lasti = 0;
				Matcher m = re.matcher(s.getString());
				while (m.find()) {
					int start = m.start();
					Log.d("substring", lasti + ":" + start);
					Log.d("substring", s.getString().substring(lasti, start));
					Log.d("match", m.group());
					Span tag = new Span(patternToTagType(re), s.getString().substring(lasti, start), m.toMatchResult());
					newstringlist.add(tag);
					lasti = m.end();
				}
				if (lasti < text.length()) {
					newstringlist.add(new Span(Tag.NONE, s.getString().substring(lasti), null));
				}
				if (aftermatch != null) {
					newstringlist.add(new Span(s.getType(), "", aftermatch));
				}
			}
			stringlist = newstringlist;
		}
		Log.d("lex", "done lexing");
		
		return stringlist;
	}
	
	public void addMessage(String text) {
		Stack<CTag> cStack = new Stack<CTag>();
		Stack<CSpan> cspanStack = new Stack<CSpan>();
		Stack<Style> styleStack = new Stack<Style>();
		Stack<StySpan> sspanStack = new Stack<StySpan>();
		
		String stripText = text;
		for (Pattern re : lexlist) {
			if (re == _url || re == _url2 || re == _img2 || re == _smile)
				continue;
			stripText = stripText.replaceAll(re.pattern(), "");
		}
		
		SpannableStringBuilder sb = new SpannableStringBuilder(stripText);
		
		Vector<Span> stringlist = lexMessage(text);
		for (Span s : stringlist) {
			String str = s.getString();
			if (str != "") {
				Log.d("span", s.getString());
			}
			MatchResult m = s.getMatch();
			if (m != null) {
				Log.d("span", m.group());
			}
		}
		
		int position = 0;
		for (Span s : stringlist) {
			switch (s.getType()) {
				case NONE:
					position += s.getString().length();
					break;
				case CTAG_BEGIN:
					cStack.push(new CTag(position, s.getMatch().group(1)));
					break;
				case CTAG_END:
					if (!cStack.empty()) {
						CTag ctag = cStack.pop();
						cspanStack.push(new CSpan(ctag.mPosition, position, ctag.mColour));
					}
					break;
				case FORMAT_BEGIN:
					styleStack.push(new Style(position, s.getMatch().group(1).toLowerCase().charAt(0)));
					break;
				case FORMAT_END:
					char style = s.getMatch().group(1).toLowerCase().charAt(0);
					boolean found = false;
					for (Style ss : styleStack) {
						if (ss.mStyle == style) {
							found = true;
							break;
						}
					}
					if (found) {
						Style sspan;
						do {
							sspan = styleStack.pop();
							sspanStack.push(new StySpan(sspan.mStart, position, sspan.mStyle));
						} while (sspan.mStyle != style);
					}
					break;
				case IMG:
					Drawable d = getResources().getDrawable(R.drawable.chummy);
			        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
			        ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
			        sb.setSpan(span, position, s.getMatch().end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
					break;
				case HANDLE:
					break;
				case MEMO:
					break;
				case ME_CMD:
					break;
				case URL:
					break;
				case URL2:
					break;
				case SMILEY:
					Drawable d2 = getResources().getDrawable(smiledict.get(s.getMatch().group()));
			        d2.setBounds(0, 0, d2.getIntrinsicWidth(), d2.getIntrinsicHeight());
			        ImageSpan span2 = new ImageSpan(d2, ImageSpan.ALIGN_BASELINE);
			        sb.setSpan(span2, position, s.getMatch().end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
					break;
				default:
					break;
			}
		}
		
		int messageLength = stripText.length();
		
		// Apply all ctags to message
		while (!cStack.empty()) {
			CTag ctag = cStack.pop();
			cspanStack.push(new CSpan(ctag.mPosition, messageLength, ctag.mColour));
		}
		
		while (!cspanStack.empty()) {
			CSpan cspan = cspanStack.pop();
			ForegroundColorSpan fcs;
			if (cspan.mColour.charAt(0) == '#') {
                fcs = new ForegroundColorSpan(Color.parseColor(cspan.mColour));
            } else if (Pattern.matches(_ctag_rgb.pattern(), cspan.mColour)) {
                String[] rgb = cspan.mColour.split("[,]");
                fcs = new ForegroundColorSpan(Color.rgb(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2])));
            } else {
                fcs = new ForegroundColorSpan(Color.parseColor(cspan.mColour));
            }
            sb.setSpan(fcs, cspan.mStart, cspan.mEnd, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		}
		
    	// Apply all styles (bold, italic, underline) to message
		while (!styleStack.empty()) {
			Log.e("style stack", "not empty");
            Style s = styleStack.pop();
            sspanStack.push(new StySpan(s.mStart, messageLength, s.mStyle));
        }
        
        while (!sspanStack.empty()) {
            StySpan sspan = sspanStack.pop();
            Log.d("StyleSpan " + sspan.mStyle, sspan.mStart + ":" + sspan.mEnd);
            if (sspan.mStyle == 'u') {
                UnderlineSpan us = new UnderlineSpan();
                sb.setSpan(us, sspan.mStart, sspan.mEnd, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            } else {
                StyleSpan ss;
                if (sspan.mStyle == 'i')
                    ss = new StyleSpan(Typeface.ITALIC);
                else if (sspan.mStyle == 'b')
                    ss = new StyleSpan(Typeface.BOLD);
                else
                    ss = new StyleSpan(Typeface.NORMAL);
                sb.setSpan(ss, sspan.mStart, sspan.mEnd, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }
        }
        
        // Apply links
        Linkify.addLinks(sb, Linkify.WEB_URLS);
        setMovementMethod(LinkMovementMethod.getInstance());
        
        // Apply emoticons
        /*Drawable d = getResources().getDrawable(R.drawable.chummy);
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
        
        
        sb.setSpan(span, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);*/
		
		super.append("\n");
    	super.append(sb);
	}
	
	public int end() {
		return getText().length();
	}

}
