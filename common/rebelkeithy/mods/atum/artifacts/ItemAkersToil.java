package rebelkeithy.mods.atum.artifacts;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemAkersToil extends ItemSpade
{



    public ItemAkersToil(int par1, EnumToolMaterial par2EnumToolMaterial) 
    {
		super(par1, par2EnumToolMaterial);
	}

	@SideOnly(Side.CLIENT)

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
    	ItemStack stack = new ItemStack(par1, 1, 0);
    	stack.addEnchantment(Enchantment.efficiency, 6);
        par3List.add(stack);
    }
}
