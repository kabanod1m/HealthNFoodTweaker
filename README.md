# HealthNFoodTweaker
A configurable Fabric mod that makes health regeneration slower and cheaper and optionally introduces passive hunger.\n
Also introduces a "sleep to heal" mechanic, for which you need to sleep until sunrise.

### Installation
Requires Minecraft 1.20.1, [Fabric Loader](https://fabricmc.net/) >=0.14.25, [Fabric API](https://www.curseforge.com/minecraft/mc-mods/fabric-api) and [Cloth Config API](https://www.curseforge.com/minecraft/mc-mods/cloth-config).

### Configuration
The mod can be configured from within the file ".minecraft/config/healthnfoodtweaker.json5" or using modmenu.
* Enable passive healing: whether passive healing is enabled. Overriden to false by gamerule naturalRegeneration false.
* Reset healing progress on damage: Whether to reset waiting time until the next passive healing tick on taking damage to "Heal every (ticks)"
* Reset exhaustion when eating: Whether to reset player's exhaustion upon eating something.

Notes:\
1 food level (or saturation if you have some) is spent when Exhaustion reaches 4.0.\
Might help a little if you for some reason prefer to have "Also disable Vanilla saturation" on true.

* Disable Vanilla healing: Whether to disable health replenishment from the Vanilla hunger system (that is fast and costs a lot of food)
* Also disable Vanilla saturation: Value "true" not recommended. Reason: you will get really hungry very fast, actions' hunger costs are tweaked to account for saturation system in Vanilla.

Notes:\
Saturation in Vanilla is a second, invisible (made visible by some mods) hunger bar that regenerates you faster if it's not empty.\
Default settings of this mod burn through saturation faster using passive hunger than through food levels but you can also disable it completely using this option.\
(Disabling it will make the game always assume saturation is 0)

* Enable passive hunger: In Vanilla Minecraft actions such as walking and standing still don't use up any food at all, meaning it's possible to stay AFK and never die from hunger. This adds passive food drain even when doing nothing, but only when food isn't drained in other ways currently.
* Heal every (ticks): Ticks until you will be passively healed 1 time after taking damage.

Notes:\
There are 20 ticks in 1 second, so 600 ticks = 30 seconds.

* Passive healing min. food level: Minimum food level to be able to heal. Set to 0 to always be able to passively heal.

Notes:\
Maximum food level is 20, so 9 = 4.5 shanks (9 half-shanks). Having 4 shanks will not allow regeneration.

* How much to passively heal: How much health must be healed every time you passively heal up.

Notes:\
Maximum health is 20, so 1 = half a heart.

* Food (exhaustion) cost for healing: How much food passive healing should cost.

Notes:\
1 food level (or saturation if you have some) is spent when Exhaustion reaches 4.0.\
For comparison, healing half a heart in Vanilla costs 6.0 Exhaustion. Jumping 1 time costs 0.05.

* Passive hunger exhaustion amount: How much exhaustion should be added each tick (1/20th of a second) if passive hunger is enabled above.

Notes:\
1 food level (or saturation if you have some) is spent when Exhaustion reaches 4.0.\
For comparison, when under the Hunger effect, each tick costs 0.005 Exhaustion. That is 10.24 times more than the default value here. (0.00048828125)

* Burn saturation faster by: By how much should Passive hunger exhaustion amount be multiplied when some saturation is present.
* Sleeping 1 food level to health: How much health 1 food level will regenerate after sleeping through a night.

Notes:\
Maximum health is 20, so 1 = half a heart.

* Sleeping food level cost: How much food levels sleeping through the night costs. To disable this behavior set 0 here.

Notes:\
Maximum food level is 20, so 5 = 2.5 shanks (5 half-shanks).

* No cost sleeping food level: Food level at which sleeping becomes cost-free and stops regenerating health.

### License
HealthNFoodTweaker is licensed under MIT.