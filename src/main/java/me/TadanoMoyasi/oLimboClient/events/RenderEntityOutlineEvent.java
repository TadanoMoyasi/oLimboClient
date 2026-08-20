package me.TadanoMoyasi.oLimboClient.events;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.Event;

public class RenderEntityOutlineEvent extends Event {
	public static final ICamera CAMERA = (ICamera)new Frustum();
	
	private final Type type;
	  
	  public Type getType() {
	    return this.type;
	  }
	  
	  private HashMap<Entity, Integer> entitiesToOutline = null;
	  
	  private HashSet<Entity> entitiesToChooseFrom;
	  
	  public HashMap<Entity, Integer> getEntitiesToOutline() {
	    return this.entitiesToOutline;
	  }
	  
	  public HashSet<Entity> getEntitiesToChooseFrom() {
	    return this.entitiesToChooseFrom;
	  }
	  
	  public RenderEntityOutlineEvent(Type theType, HashSet<Entity> potentialEntities) {
	    this.type = theType;
	    this.entitiesToChooseFrom = potentialEntities;
	    if (potentialEntities != null)
	      this.entitiesToOutline = new HashMap<>(potentialEntities.size()); 
	  }
	  
	  public void queueEntitiesToOutline(Function<Entity, Integer> outlineColor) {
	    if (outlineColor == null)
	      return; 
	    if (this.entitiesToChooseFrom == null)
	      computeAndCacheEntitiesToChooseFrom(); 
	    Iterator<Entity> itr = this.entitiesToChooseFrom.iterator();
	    while (itr.hasNext()) {
	      Entity e = itr.next();
	      Integer i = outlineColor.apply(e);
	      if (i != null) {
	        this.entitiesToOutline.put(e, i);
	        itr.remove();
	      } 
	    } 
	  }
	  
	  public void queueEntityToOutline(Entity entity, int outlineColor) {
	    if (entity == null)
	      return; 
	    if (this.entitiesToChooseFrom == null)
	      computeAndCacheEntitiesToChooseFrom(); 
	    if (!this.entitiesToChooseFrom.contains(entity))
	      return; 
	    this.entitiesToOutline.put(entity, Integer.valueOf(outlineColor));
	    this.entitiesToChooseFrom.remove(entity);
	  }
	  
	  private void computeAndCacheEntitiesToChooseFrom() {
	    List<Entity> entities = (Minecraft.getMinecraft()).theWorld.getLoadedEntityList();
	    this.entitiesToChooseFrom = new HashSet<>(entities.size());
	    entities.forEach(e -> {
	          if (e == null)
	            return; 
	          if (!CAMERA.isBoundingBoxInFrustum(e.getEntityBoundingBox()))
	            return; 
	          if ((!(e instanceof net.minecraft.entity.item.EntityArmorStand) || !e.isInvisible()) && !(e instanceof net.minecraft.entity.item.EntityItemFrame))
	            this.entitiesToChooseFrom.add(e); 
	        });
	    this.entitiesToOutline = new HashMap<>(this.entitiesToChooseFrom.size());
	  }
	  
	  public enum Type {
	    XRAY, NO_XRAY;
	  }
}
