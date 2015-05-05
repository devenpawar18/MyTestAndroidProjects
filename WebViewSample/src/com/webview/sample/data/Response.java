package com.webview.sample.data;

public class Response {

	public static String htmlInfoTemplate =  "<html><head><meta name=\"viewport\" content=\"width=device-width, " + 
	"initial-scale=1, user-scalable=no\" /><style type=\"text/css\">h1," + 
	" h2 { padding:0px; margin:0px; }html { font-family:Helvetica; padding:6px; " + 
	"}h1 { font-size:$maxsize$px; margin:6px 0px 12px 0px; }h2 " + 
	"{ font-size:" + "$maxsize1$px; margin:12px 0px 0px 0px; }img { max-width:100%;" +
	" height:auto; margin-bottom:4px; }a { color:#f95602; text-decoration:none;" +
	" font-weight:bold; }embed, object { width:100%; }.pubDate " + "{ font-size:" + "$maxsize2$" + 
	"px; color:#666666; }.info { font-size:"+ "$infosize$" + "px; " +
	"font-weight:normal; border-top:1px dotted black; padding:5px 0px 5px 0px; }.intro" + 
	" { font-size:" + "$introsize$" + "px; font-weight:bold; margin:14px 0px 14px 0px; overflow:hidden; }." +
	"imageContainer { font-size:" + "$infosize$" + "px; font-style:italic; margin-top: " + 
	"4px; margin-right: 10px; float: left; }.body { font-size:" + "$bodysize$" +
	"px; overflow:hidden;}." + "info span { white-space:nowrap; }</style></head><body><h1>$title$</h1>" +
	"<div class=\"info\"><span>Door: $author$</span> | <span>$date$</span></div>" + 
	"<div class=\"intro\"><div class=\"imageContainer\">" +
	"<img src=\"$imageUrl$\" width=\"95\" height=\"130\"  /></div>$intro$</div>" + 
	"<div class=\"body\">$body$</div></body></html>";

		// +
		// "<div class=\"intro\">Digital Life, overal en altijd up-to-date van het laatste nieuws uit"
		// +
		// "de digitale wereld en entertainment.</div><div class=\"body\">Website:"
		// + " "
		// +
		// "<a href=\"http://digitallife.nl\">http://digitallife.nl</a><br/>Facebook:"
		// +
		// "<a href=\"http://facebook.com/digitallife.nl\">http://facebook.com/digitallife.nl</a>"
		// +
		// "<br/>Twitter: <a href=\"http://twitter.com/digitallifenl\">http://twitter.com/digitallifenl</a>"
		// +
		// "<br/><br/>Versie: 1.0<br/>Uitgever: <a href=\"http://idg.nl\">IDG Nederland</a>, "
		// +
		// "Amsterdam<br/>Contact: <a href=\"mailto:redactie@digitallife.nl\">redactie@digitallife.nl"
		// +
		// "</a><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;of"
		// +
		// " <a href=\"tel://+31207585955\">+31 (0) 20 758 59 55</a><br/>Ontwikkelaar: "
		// +
		// "<a href=http://cdnbakmi.kaltura.com/p/623862/sp/62386200/serveFlavor/flavorId/1_syb2br5b/name/1_syb2br5b.mp3>Sogeti</a></div>"
		// + "</body></html>";

	// "<html><head><style type=\"text/css\">html "
	// +
	// "{ font-family:Helvetica; padding:0px; }a { color:#f95602; text-decoration:none; "
	// +
	// "font-weight:bold; }.intro { font-size:17px; font-weight:bold; margin:0px 0px 10px 0px; }"
	// +
	// ".body { font-size:16px; }</style></head><body leftmargin=\"0\" topmargin=\"0\">"
	// +
	// "<script type='text/javascript' src='http://html5.kaltura.org/js'></script> <object id='kaltura_player' name='kaltura_player'"
	// + "type='application/x-shockwave-flash'"
	// + " allowFullScreen='true' allowNetworking='all'"
	// + "allowScriptAccess='always' height='300' width='400'"
	// +
	// "data='http://www.kaltura.com/index.php/kwidget/cache_st/1274763304/wid/_623862/uiconf_id/5349042/entry_id/1_aum77abd'>"
	// + "<param name='allowFullScreen' value='true' />"
	// + "<param name='allowNetworking' value='all' />"
	// + "<param name='allowScriptAccess' value='always' />"
	// + "<param name='bgcolor' value='#000000' />"
	// + "<param name='flashVars' value='&' />"
	// +
	// "<param name='movie' value='http://www.kaltura.com/index.php/kwidget/cache_st/1274763304/wid/_623862/uiconf_id/5349042/entry_id/1_aum77abd' />"
	// + "</object>"
	//
	// + "</body></html>";

	// +
	// "<div class=\"intro\">Digital Life, overal en altijd up-to-date van het laatste nieuws uit"
	// +
	// "de digitale wereld en entertainment.</div><div class=\"body\">Website:"
	// + " "
	// +
	// "<a href=\"http://digitallife.nl\">http://digitallife.nl</a><br/>Facebook:"
	// +
	// "<a href=\"http://facebook.com/digitallife.nl\">http://facebook.com/digitallife.nl</a>"
	// +
	// "<br/>Twitter: <a href=\"http://twitter.com/digitallifenl\">http://twitter.com/digitallifenl</a>"
	// +
	// "<br/><br/>Versie: 1.0<br/>Uitgever: <a href=\"http://idg.nl\">IDG Nederland</a>, "
	// +
	// "Amsterdam<br/>Contact: <a href=\"mailto:redactie@digitallife.nl\">redactie@digitallife.nl"
	// +
	// "</a><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;of"
	// +
	// " <a href=\"tel://+31207585955\">+31 (0) 20 758 59 55</a><br/>Ontwikkelaar: "
	// +
	// "<a href=http://cdnbakmi.kaltura.com/p/623862/sp/62386200/serveFlavor/flavorId/1_syb2br5b/name/1_syb2br5b.mp3>Sogeti</a></div>"
	// + "</body></html>";

	// "<a href=http://www.sogeti.nl>Sogeti</a></div>"

	// "<html><head><style type=\"text/css\">html " +
	// "{ font-family:Helvetica; padding:0px; }a { color:#f95602; text-decoration:none; "
	// +
	// "font-weight:bold; }.intro { font-size:17px; font-weight:bold; margin:0px 0px 10px 0px; }"
	// +
	// ".body { font-size:16px; }</style></head><body leftmargin=\"0\" topmargin=\"0\">"
	// +
	// "<div class=\"intro\">Digital Life, overal en altijd up-to-date van het laatste nieuws uit"
	// + "de digitale wereld en entertainment.</div><div class=\"body\">" +
	// "<p><a href=tel://+31207585955>call</a></p></body></html>";

}
