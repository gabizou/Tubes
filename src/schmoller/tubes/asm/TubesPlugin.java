package schmoller.tubes.asm;

import java.awt.Desktop;
import java.io.File;
import java.util.Map;

import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import cpw.mods.fml.common.versioning.DefaultArtifactVersion;
import cpw.mods.fml.common.versioning.VersionParser;
import cpw.mods.fml.relauncher.FMLInjectionData;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

public class TubesPlugin implements IFMLLoadingPlugin
{
	public static File location;
	
	@Override
	@Deprecated
	public String[] getLibraryRequestClass()
	{
		return null;
	}

	@Override
	public String[] getASMTransformerClass()
	{
		versionCheck("[1.6.2,1.6.4]", "Tubes");
		return new String[] {"schmoller.tubes.asm.MinecraftTransformer", "schmoller.tubes.asm.APIChecker"};
	}

	public static void versionCheck(String reqVersion, String mod)
    {
        String mcVersion = (String) FMLInjectionData.data()[4];
        if(!VersionParser.parseRange(reqVersion).containsVersion(new DefaultArtifactVersion(mcVersion)))
        {
            String err = "This version of "+mod+" does not support minecraft version "+mcVersion;
            System.err.println(err);
            
            JEditorPane ep = new JEditorPane("text/html", 
                    "<html>" +
                    err + 
                    "<br>Remove it from your mods folder and check <a href=\"http://www.minecraftforum.net/topic/2071224-\">here</a> for updates" +
                    "</html>");

            ep.setEditable(false);
            ep.setOpaque(false);
            ep.addHyperlinkListener(new HyperlinkListener()
            {
                @Override
                public void hyperlinkUpdate(HyperlinkEvent event)
                {
                    try
                    {
                        if (event.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED))
                            Desktop.getDesktop().browse(event.getURL().toURI());
                    }
                    catch(Exception e)
                    {}
                }
            });
            
            JOptionPane.showMessageDialog(null, ep, "Fatal error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
	
	@Override
	public String getModContainerClass()
	{
		return "schmoller.tubes.asm.TubesContainer";
	}

	@Override
	public String getSetupClass()
	{
		return null;
	}

	@Override
	public void injectData( Map<String, Object> data )
	{
		if(data.containsKey("coremodLocation"))
            location = (File)data.get("coremodLocation");
	}

	
}
