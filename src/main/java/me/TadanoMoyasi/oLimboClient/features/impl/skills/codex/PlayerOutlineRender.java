package me.TadanoMoyasi.oLimboClient.features.impl.skills.codex;

public class PlayerOutlineRender {
	//後で作る
    /*private static final Minecraft mc = Minecraft.getMinecraft();
        
    private static final CachedInfo entityRenderCache = new CachedInfo();
    
    private static boolean stopLookingForOptifine = false;
    
    private static Method isFastRender = null;
    
    private static Method isShaders = null;
    
    private static Method isAntialiasing = null;
    
    private static Framebuffer swapBuffer = null;
    
    private static Framebuffer initSwapBuffer() {
      Framebuffer main = Minecraft.getMinecraft().getFramebuffer();
      Framebuffer framebuffer = new Framebuffer(main.framebufferTextureWidth, main.framebufferTextureHeight, true);
      framebuffer.setFramebufferFilter(9728);
      framebuffer.setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F);
      return framebuffer;
    }
    
    private static void updateFramebufferSize() {
      if (swapBuffer == null)
        swapBuffer = initSwapBuffer(); 
      int width = (Minecraft.getMinecraft()).displayWidth;
      int height = (Minecraft.getMinecraft()).displayHeight;
      if (swapBuffer.framebufferWidth != width || swapBuffer.framebufferHeight != height)
        swapBuffer.createBindFramebuffer(width, height); 
      RenderGlobal rg = (Minecraft.getMinecraft()).renderGlobal;
      Framebuffer outlineBuffer = rg.entityOutlineFramebuffer;
      if (outlineBuffer.framebufferWidth != width || outlineBuffer.framebufferHeight != height) {
        outlineBuffer.createBindFramebuffer(width, height);
        rg.entityOutlineShader.createBindFramebuffers(width, height);
      } 
    }
    
    public static boolean renderEntityOutlines(ICamera camera, float partialTicks, double x, double y, double z) {
      boolean shouldRenderOutlines = shouldRenderEntityOutlines();
      if (shouldRenderOutlines && !isCacheEmpty() && MinecraftForgeClient.getRenderPass() == 0) {
        Minecraft mc = Minecraft.getMinecraft();
        RenderGlobal renderGlobal = mc.renderGlobal;
        RenderManager renderManager = mc.getRenderManager();
        mc.theWorld.theProfiler.endStartSection("entityOutlines");
        updateFramebufferSize();
        renderGlobal.entityOutlineFramebuffer.framebufferClear();
        renderGlobal.entityOutlineFramebuffer.bindFramebuffer(false);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableFog();
        mc.getRenderManager().setRenderOutlines(true);
        DrawUtils.enableOutlineMode();
        if (!isXrayCacheEmpty()) {
          GlStateManager.depthFunc(519);
          for (Map.Entry<Entity, Integer> entityAndColor : entityRenderCache.getXrayCache().entrySet()) {
            if (shouldRender(camera, entityAndColor.getKey(), x, y, z))
              try {
                if (!(entityAndColor.getKey() instanceof EntityLivingBase))
                  DrawUtils.outlineColor(((Integer)entityAndColor.getValue()).intValue()); 
                renderManager.renderEntityStatic(entityAndColor.getKey(), partialTicks, true);
              } catch (Exception exception) {} 
          } 
          GlStateManager.depthFunc(515);
        } 
        if (!isNoXrayCacheEmpty()) {
          if (!isNoOutlineCacheEmpty()) {
            swapBuffer.framebufferClear();
            copyBuffers(mc.getFramebuffer(), swapBuffer, 256);
            swapBuffer.bindFramebuffer(false);
            if (entityRenderCache.getNoOutlineCache() != null)
              for (Entity entity : entityRenderCache.getNoOutlineCache()) {
                if (shouldRender(camera, entity, x, y, z))
                  try {
                    renderManager.renderEntityStatic(entity, partialTicks, true);
                  } catch (Exception exception) {} 
              }  
            copyBuffers(swapBuffer, renderGlobal.entityOutlineFramebuffer, 256);
            renderGlobal.entityOutlineFramebuffer.bindFramebuffer(false);
          } else {
            copyBuffers(mc.getFramebuffer(), renderGlobal.entityOutlineFramebuffer, 256);
          } 
          for (Map.Entry<Entity, Integer> entityAndColor : entityRenderCache.getNoXrayCache().entrySet()) {
            if (shouldRender(camera, entityAndColor.getKey(), x, y, z))
              try {
                if (!(entityAndColor.getKey() instanceof EntityLivingBase))
                  DrawUtils.outlineColor(((Integer)entityAndColor.getValue()).intValue()); 
                renderManager.renderEntityStatic(entityAndColor.getKey(), partialTicks, true);
              } catch (Exception exception) {} 
          } 
        } 
        DrawUtils.disableOutlineMode();
        RenderHelper.enableStandardItemLighting();
        mc.getRenderManager().setRenderOutlines(false);
        GlStateManager.depthMask(false);
        renderGlobal.entityOutlineShader.loadShaderGroup(partialTicks);
        GlStateManager.depthMask(true);
        GlStateManager.enableLighting();
        mc.getFramebuffer().bindFramebuffer(false);
        GlStateManager.enableFog();
        GlStateManager.enableBlend();
        GlStateManager.enableColorMaterial();
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
      } 
      return !shouldRenderOutlines;
    }
    
    public static Integer getCustomOutlineColor(EntityLivingBase entity) {
      if (entityRenderCache.getXrayCache() != null && entityRenderCache.getXrayCache().containsKey(entity)) {
        int color = ((Integer)entityRenderCache.getXrayCache().get(entity)).intValue();
        if (color == ColorCode.CHROMA.getColor())
          return Integer.valueOf(ManualChromaManager.getChromaColor(0.0F, 0.0F, 255)); 
        return Integer.valueOf(color);
      } 
      if (entityRenderCache.getNoXrayCache() != null && entityRenderCache.getNoXrayCache().containsKey(entity)) {
        int color = ((Integer)entityRenderCache.getNoXrayCache().get(entity)).intValue();
        if (color == ColorCode.CHROMA.getColor())
          return Integer.valueOf(ManualChromaManager.getChromaColor(0.0F, 0.0F, 255)); 
        return Integer.valueOf(color);
      } 
      return null;
    }
    
    public static boolean shouldRenderEntityOutlines() {
      Minecraft mc = Minecraft.getMinecraft();
      RenderGlobal renderGlobal = mc.renderGlobal;
      if (renderGlobal.entityOutlineFramebuffer == null || renderGlobal.entityOutlineShader == null || mc.thePlayer == null) return false; 
      if (mc.thePlayer.getHeldItem() == null) return false;
      if (!mc.thePlayer.getHeldItem().getDisplayName().contains("Codex")) return false;
      if (!stopLookingForOptifine && isFastRender == null)
        try {
          Class<?> config = Class.forName("Config");
          try {
            isFastRender = config.getMethod("isFastRender", new Class[0]);
            isShaders = config.getMethod("isShaders", new Class[0]);
            isAntialiasing = config.getMethod("isAntialiasing", new Class[0]);
          } catch (Exception ex) {
            System.err.println("Couldn't find Optifine methods for entity outlines.");
            stopLookingForOptifine = true;
          } 
        } catch (Exception ex) {
          System.err.println("Couldn't find Optifine for entity outlines.");
          stopLookingForOptifine = true;
        }  
      boolean isFastRenderValue = false;
      boolean isShadersValue = false;
      boolean isAntialiasingValue = false;
      if (isFastRender != null)
        try {
          isFastRenderValue = ((Boolean)isFastRender.invoke(null, new Object[0])).booleanValue();
          isShadersValue = ((Boolean)isShaders.invoke(null, new Object[0])).booleanValue();
          isAntialiasingValue = ((Boolean)isAntialiasing.invoke(null, new Object[0])).booleanValue();
        } catch (IllegalAccessException|java.lang.reflect.InvocationTargetException ex) {
          System.out.println("An error occurred while calling Optifine methods for entity outlines...");
        }  
      return (!isFastRenderValue && !isShadersValue && !isAntialiasingValue);
    }
    
    private static boolean shouldRender(ICamera camera, Entity entity, double x, double y, double z) {
      Minecraft mc = Minecraft.getMinecraft();
      if (entity == mc.getRenderViewEntity() && (
        !(mc.getRenderViewEntity() instanceof EntityLivingBase) || !((EntityLivingBase)mc.getRenderViewEntity()).isPlayerSleeping()) && mc.gameSettings.thirdPersonView == 0)
        return false; 
      return (mc.theWorld.isBlockLoaded(new BlockPos(entity)) && (mc.getRenderManager().shouldRender(entity, camera, x, y, z) || entity.riddenByEntity == mc.thePlayer));
    }
    
    private static void copyBuffers(Framebuffer frameToCopy, Framebuffer frameToPaste, int buffersToCopy) {
      if (OpenGlHelper.isFramebufferEnabled()) {
        OpenGlHelper.glBindFramebuffer(36008, frameToCopy.framebufferObject);
        OpenGlHelper.glBindFramebuffer(36009, frameToPaste.framebufferObject);
        GL30.glBlitFramebuffer(0, 0, frameToCopy.framebufferWidth, frameToCopy.framebufferHeight, 0, 0, frameToPaste.framebufferWidth, frameToPaste.framebufferHeight, buffersToCopy, 9728);
      } 
    }
    
    public static boolean isCacheEmpty() {
      return (isXrayCacheEmpty() && isNoXrayCacheEmpty());
    }
    
    private static boolean isXrayCacheEmpty() {
      return (entityRenderCache.xrayCache == null || entityRenderCache.xrayCache.isEmpty());
    }
    
    private static boolean isNoXrayCacheEmpty() {
      return (entityRenderCache.noXrayCache == null || entityRenderCache.noXrayCache.isEmpty());
    }
    
    private static boolean isNoOutlineCacheEmpty() {
      return (entityRenderCache.noOutlineCache == null || entityRenderCache.noOutlineCache.isEmpty());
    }
    
    private static boolean emptyLastTick = false;
    
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
      if (event.phase == TickEvent.Phase.START) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.theWorld != null && shouldRenderEntityOutlines()) {
          RenderEntityOutlineEvent xrayOutlineEvent = new RenderEntityOutlineEvent(RenderEntityOutlineEvent.Type.XRAY, null);
          MinecraftForge.EVENT_BUS.post((Event)xrayOutlineEvent);
          RenderEntityOutlineEvent noxrayOutlineEvent = new RenderEntityOutlineEvent(RenderEntityOutlineEvent.Type.NO_XRAY, xrayOutlineEvent.getEntitiesToChooseFrom());
          MinecraftForge.EVENT_BUS.post((Event)noxrayOutlineEvent);
          entityRenderCache.setXrayCache(xrayOutlineEvent.getEntitiesToOutline());
          entityRenderCache.setNoXrayCache(noxrayOutlineEvent.getEntitiesToOutline());
          entityRenderCache.setNoOutlineCache(noxrayOutlineEvent.getEntitiesToChooseFrom());
          if (isCacheEmpty()) {
            if (!emptyLastTick)
              mc.renderGlobal.entityOutlineFramebuffer.framebufferClear(); 
            emptyLastTick = true;
          } else {
            emptyLastTick = false;
          } 
        } else if (!emptyLastTick) {
          entityRenderCache.setXrayCache(null);
          entityRenderCache.setNoXrayCache(null);
          entityRenderCache.setNoOutlineCache(null);
          if (mc.renderGlobal.entityOutlineFramebuffer != null)
            mc.renderGlobal.entityOutlineFramebuffer.framebufferClear(); 
          emptyLastTick = true;
        } 
      } 
    }
    
    private static class CachedInfo {
      public void setXrayCache(HashMap<Entity, Integer> xrayCache) {
        this.xrayCache = xrayCache;
      }
      
      public void setNoXrayCache(HashMap<Entity, Integer> noXrayCache) {
        this.noXrayCache = noXrayCache;
      }
      
      public void setNoOutlineCache(HashSet<Entity> noOutlineCache) {
        this.noOutlineCache = noOutlineCache;
      }
      
      private HashMap<Entity, Integer> xrayCache = null;
      
      public HashMap<Entity, Integer> getXrayCache() {
        return this.xrayCache;
      }
      
      private HashMap<Entity, Integer> noXrayCache = null;
      
      public HashMap<Entity, Integer> getNoXrayCache() {
        return this.noXrayCache;
      }
      
      private HashSet<Entity> noOutlineCache = null;
      
      public HashSet<Entity> getNoOutlineCache() {
        return this.noOutlineCache;
      }
      
      private CachedInfo() {}
    
    
	//こちらが！1週間掛けて作ったけど結局あきらめてSkyBlockAddonsで使われてた方式に切り替える事になった旧PlayerOutlineRender、のりトッピングです！うっひょ～～～～～～！
    //SBA方式初見時、OptiFineの一部機能に干渉するのを見て、大きな声を出したら、もやしさんからの誠意で、旧Renderをサービスしてもらいました！
    //俺のコード次第でこの干渉潰すことだってできるんだぞってことで、いただきま～～～～す！まずは旧Renderから
    //コラ～！これでもかってくらいグチャグチャの旧Renderの中には、バグが入っており、怒りのあまり、旧Renderを全部倒してしまいました～！
    //すっかりもやしも立場をわきまえ、誠意のSBA方式をもらったところで、お次に、圧倒的存在感のSBA方式を啜る～！ 殺すぞ～！
    //ワシワシとした食感のSBA方式の中には、神コードが入っており、さすがのMOYASIも、厨房に入っていってしまいました～！
    //ちなみに、旧Renderが土下座している様子は、ぜひサブチャンネルをご覧ください！

    //p.s. 独自方式なんてものはやるもんじゃないです。が、かなりopenGLや、マインクラフトへの理解が深まった気がするので嬉しいです。
    //今回、「完成品を作る」という意味では遠回りでしたが、私自身の成長にはつながったので一旦良しとしたいと思います。また気が向いたら自前の方式でも作ってみたいものですね。
    //それと、今回は流石に許容できる時間を超えてしまったので諦めましたが、一応F5視点のライティングが少しバグる(建物の影がちょこちょこなくなる)くらいで使えるようにはなっていました。
    //が、私は完璧を目指した。目指したかった。結果がこちらです。さようなら時間。いらっしゃい知識。これからも頑張っていく所存です。

	/*private static final double MAX_DISTANCE = 7.0D;
	private static boolean isRendering = false;
	
	private static final Field renderPosXField = ReflectionHelper.findField(RenderManager.class, "renderPosX", "field_78725_b");
    private static final Field renderPosYField = ReflectionHelper.findField(RenderManager.class, "renderPosY", "field_78726_c");
    private static final Field renderPosZField = ReflectionHelper.findField(RenderManager.class, "renderPosZ", "field_78723_d");

    static {
        renderPosXField.setAccessible(true);
        renderPosYField.setAccessible(true);
        renderPosZField.setAccessible(true);
    }
    
    private static final ModelBiped ARMOR_LEGS = new ModelBiped(0.5F);
    private static final ModelBiped ARMOR = new ModelBiped(1.0F);

    public static void init() {
    	Framebuffer framebuffer = mc.getFramebuffer();
        framebuffer.enableStencil();
    }
    
    //PreとPost使う方式はどうやってもバグったので捨てました。やっぱLastなんよ
    public static void onRenderWorldLast(RenderWorldLastEvent event) {
        if (mc.thePlayer == null || mc.thePlayer.getHeldItem() == null) return;
        if (!mc.thePlayer.getHeldItem().getDisplayName().contains("Codex")) return;
        for (EntityPlayer player : mc.theWorld.playerEntities) {
            if (player == mc.thePlayer) continue;
            if (mc.thePlayer.getDistanceSqToEntity(player)
                    > MAX_DISTANCE * MAX_DISTANCE) {
                continue;
            }
            NetworkPlayerInfo info = mc.getNetHandler().getPlayerInfo(player.getUniqueID());
            if (info == null) continue;
            drawPlayerOutline(player, event.partialTicks);
        }
        GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT);
        GL11.glStencilMask(0xFF);
        GL11.glStencilFunc(GL11.GL_ALWAYS, 0, 0xFF);
        GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_KEEP);
        GL11.glDisable(GL11.GL_STENCIL_TEST);
    }
    
    private static void drawPlayerOutline(EntityPlayer player, float partialTicks) {
    	GlStateManager.pushMatrix();
        GlStateManager.pushAttrib();	
        try {
        	RenderManager renderManager = mc.getRenderManager();
            GL11.glEnable(GL11.GL_STENCIL_TEST);
            GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT);
            GL11.glStencilMask(0xFF);
            GL11.glStencilFunc(GL11.GL_ALWAYS, 1, 0xFF);// 常に成功
            GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_REPLACE);// 成功した場所1
                        
            Render<?> render = renderManager.getEntityRenderObject(player);
            double x = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks - renderManager.viewerPosX;
            double y = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks - renderManager.viewerPosY;
            double z = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks - renderManager.viewerPosZ;
            
            GlStateManager.translate(x, y, z);
            
         	// RenderLivingBase.rotateCorpse()
            float f5 = player.prevLimbSwingAmount + (player.limbSwingAmount - player.prevLimbSwingAmount) *partialTicks;
        	
        	float f6 = player.limbSwing - player.limbSwingAmount * (1.0F - partialTicks);
        	
        	float bodyYaw = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * partialTicks;
        	
        	float headYaw = player.prevRotationYawHead + (player.rotationYawHead - player.prevRotationYawHead) * partialTicks;
        	
        	float pitch = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * partialTicks;
        	
        	float headYawRelative = headYaw - bodyYaw;
            
            GlStateManager.rotate(180.0F - bodyYaw, 0.0F, 1.0F, 0.0F);
            GlStateManager.scale(-1.0F, -1.0F, 1.0F);
            GlStateManager.translate(0.0F, -1.5078125F, 0.0F);
            GlStateManager.colorMask(false, false, false, false);
            GlStateManager.depthMask(false);
            GlStateManager.scale(1.0F, 1.0F, 1.0F);
            GlStateManager.disableTexture2D();
            
            //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
            //確かここの下のif文と中全部消して
            //renderManager.renderEntitySimple(player, partialTicks);
            //とかにしたら建物の影とかがバグるけどちゃんと使えた気がするので皆様ぜひやってみてください。
            //■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
            if (render instanceof RenderPlayer) {
            	RenderPlayer renderPlayer = (RenderPlayer) render;
            	ModelBase model = renderPlayer.getMainModel();
            		
            	renderPlayerModel(player, model,  partialTicks);
            	renderArmorModel(player, partialTicks);
            }
            
            GlStateManager.colorMask(true, true, true, true);
            GlStateManager.depthMask(true);
            GL11.glStencilFunc(GL11.GL_NOTEQUAL, 1, 0xFF); // Stencil = 1 のところは描画しない
            GL11.glStencilMask(0x00);
            GlStateManager.disableLighting();
            
            //GlStateManager.disableDepth();
            
            GlStateManager.color(1.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.scale(1.25F, 1.03F, 1.25F);//どんだけデカくしても防具に隠れるからヤケクソ1.25
            
            //float yaw = player.prevRotationYawHead + (player.rotationYawHead - player.prevRotationYawHead) * partialTicks;
            
            //GlStateManager.rotate(180.0F - yaw, 0.0F, -1.0F, 0.0F);
            //GlStateManager.scale(1.03F, 1.03F, 1.03F);
            
            if (render instanceof RenderPlayer) {
            	RenderPlayer renderPlayer = (RenderPlayer) render;
            	ModelBase model = renderPlayer.getMainModel();
            	
            	model.setLivingAnimations(player, f6, f5, partialTicks);
            	
            	//model.setRotationAngles(f6, f5, player.ticksExisted + partialTicks, headYawRelative, pitch, 0.0625F, player);
            	
            	model.render(player, f6, f5, player.ticksExisted + partialTicks, headYawRelative, pitch, 0.0625F);
            }
            GL11.glStencilMask(0xFF);
        } finally {
        	GL11.glDisable(GL11.GL_STENCIL_TEST);
            GlStateManager.colorMask(true, true, true, true);
            GlStateManager.popMatrix();
            GlStateManager.popAttrib();
        }
    }
    
    private static void renderPlayerModel(EntityPlayer player, ModelBase model, float partialTicks) {
        float limbSwingAmount = player.prevLimbSwingAmount + (player.limbSwingAmount - player.prevLimbSwingAmount) * partialTicks;
        float limbSwing = player.limbSwing - player.limbSwingAmount * (1.0F - partialTicks);
        float bodyYaw =player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * partialTicks;
        float headYaw =player.prevRotationYawHead + (player.rotationYawHead - player.prevRotationYawHead) * partialTicks;
        float pitch =player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * partialTicks;
        float headYawRelative = headYaw - bodyYaw;

        model.setLivingAnimations(player, limbSwing, limbSwingAmount, partialTicks);
        model.render(player, limbSwing, limbSwingAmount, player.ticksExisted + partialTicks, headYawRelative, pitch, 0.0625F);
    }
    
    private static void setupArmorModel(ModelBiped model, int armorSlot) {
        model.setInvisible(false);

        model.bipedHead.showModel = false;
        model.bipedHeadwear.showModel = false;
        model.bipedBody.showModel = false;
        model.bipedRightArm.showModel = false;
        model.bipedLeftArm.showModel = false;
        model.bipedRightLeg.showModel = false;
        model.bipedLeftLeg.showModel = false;

        switch (armorSlot) {
            case 1:
                model.bipedRightLeg.showModel = true;
                model.bipedLeftLeg.showModel = true;
                break;
            case 2:
                model.bipedBody.showModel = true;
                model.bipedRightLeg.showModel = true;
                model.bipedLeftLeg.showModel = true;
                break;
            case 3:
                model.bipedBody.showModel = true;
                model.bipedRightArm.showModel = true;
                model.bipedLeftArm.showModel = true;
                break;
            case 4:
                model.bipedHead.showModel = true;
                model.bipedHeadwear.showModel = true;
                break;
        }
    }
    
    private static void renderArmorModel(EntityPlayer player, float partialTicks) {
    	renderArmorSlot(player, 4, partialTicks);
        renderArmorSlot(player, 3, partialTicks);
        renderArmorSlot(player, 2, partialTicks);
        renderArmorSlot(player, 1, partialTicks);
    }
    
    private static void renderArmorSlot(EntityPlayer player, int slot, float partialTicks) {
    	ItemStack stack = player.getCurrentArmor(slot - 1);
        if (stack == null) return;
        if (!(stack.getItem() instanceof ItemArmor)) return;
        ModelBiped model;
        if (slot == 2) {
            model = ARMOR_LEGS;
        } else {
            model = ARMOR;
        }
        Render<?> render = mc.getRenderManager().getEntityRenderObject(player);
        if (!(render instanceof RenderPlayer)) return;
        RenderPlayer renderPlayer = (RenderPlayer) render;
        ModelBiped mainModel = (ModelBiped) renderPlayer.getMainModel();
        model.setModelAttributes(mainModel);
        float limbSwingAmount = player.prevLimbSwingAmount + (player.limbSwingAmount - player.prevLimbSwingAmount) * partialTicks;
        float limbSwing =player.limbSwing - player.limbSwingAmount * (1.0F - partialTicks);
        model.setLivingAnimations(player, limbSwing, limbSwingAmount, partialTicks);
        model.setInvisible(true);
        switch (slot) {
        case 4:
            model.bipedHead.showModel = true;
            model.bipedHeadwear.showModel = true;
            break;
        case 3:
            model.bipedBody.showModel = true;
            model.bipedRightArm.showModel = true;
            model.bipedLeftArm.showModel = true;
            break;
        case 2:
            model.bipedBody.showModel = true;
            model.bipedRightLeg.showModel = true;
            model.bipedLeftLeg.showModel = true;
            break;
        case 1:
            model.bipedRightLeg.showModel = true;
            model.bipedLeftLeg.showModel = true;
            break;
    }
    model.render(player, limbSwing, limbSwingAmount, player.ticksExisted + partialTicks, 0.0F, 0.0F, 0.0625F);
    }*/
}
