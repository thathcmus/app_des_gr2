package com.example.plant.activities.Article

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.plant.R
import com.example.plant.adapter.ArticleRecyclerAdapter
import com.example.plant.constant.constant
import com.example.plant.model.Article

class ArticlesActivity : AppCompatActivity(), ArticleRecyclerAdapter.MyClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ArticleRecyclerAdapter
    private lateinit var moveList: ArrayList<Article>
    private lateinit var image: Array<Int>
    private lateinit var title: Array<String>
    private lateinit var name: Array<String>
    private lateinit var day: Array<String>
    private lateinit var avatar: Array<Int>
    private lateinit var descriptionArticles: Array<String>
    private lateinit var detailArticles: Array<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)
        supportActionBar?.hide()



        title = arrayOf(
            "Dry farming could help agriculture in the western U.S. amid climate change",
            "Plant/animal hybrid proteins could help crops fend off diseases",
            "The oldest known pollen-carrying insects lived about 280 million years ago",
            "A Caribbean island gets everyone involved in protecting beloved species",
            "How Kenyans help themselves and the planet by saving mangrove trees",
            "The worldwide water-lifting power of plants is enormous",
            "Flower shape and size impact bees’ chances of catching gut parasites",
            "These flowers lure pollinators to their deaths. There’s a new twist on how",
            "Africa’s fynbos plants hold their ground with the world’s thinnest roots",
            "Earth may have 9,200 more tree species than previously thought"
        )
        name = arrayOf(
            "Katherine Kornei",
            "Erin Garcia de Jesús",
            "Sid Perkins",
            "Anna Gibbs",
            "Geoffrey Kamadi",
            "Sid Perkins",
            "Rachel Crowell",
            "Susan Milius",
            "Jake Buehler",
            "Jude Coleman"
        )
        day = arrayOf(
            "March 9, 2023",
            "March 2, 2023",
            "February 28, 2023",
            "September 27, 2022",
            "September 14, 2022",
            "September 6, 2022",
            "July 12, 2022",
            "April 19, 2022",
            "March 1, 2022",
            "February 7, 2022"
        )
        descriptionArticles = arrayOf(
            "Forgoing irrigation can save water and produce more flavorful fruits and vegetables",
            "'Pikobodies' loan plant immune systems a uniquely animal trait: flexibility",
            "Ground-dwelling Tillyardembia was probably picky about which trees it climbed for pollen",
            "Tiny Saba has a wealth of beloved species in need of support",
            "Conserving the carbon-storing forests is good for the environment and village growth",
            "Forests alone use 9.4 quadrillion watt-hours of energy annually",
            "Parasite transmission was lower when the insects landed on long, narrow flowers",
            "Two species of jack-in-the-pulpits may use sex scents to lure male fungus gnats",
            "The fynbos shrubland weaponizes its very thin roots in the race to suck up nutrients",
            "More than a third are probably hiding out in South America, researchers say"
        )
        detailArticles = arrayOf(
            "By Katherine Kornei\n" +
                    "\n" +
                    "MARCH 9, 2023 AT 9:00 AM\n" +
                    "\n" +
                    "In the parking lot behind a grocery store in Portland, Ore., last September, several hundred tomato aficionados gathered on a sunny, breezy day for Tomato Fest. While many attendees devoured slices of tomato quiche and admired garlands of tomatoes with curiously pointed ends, I beelined to a yellow-tented booth hosted by Oregon State University. Agricultural researcher Matt Davis was handing out samples of experimental tomatoes.\n" +
                    "\n" +
                    "I took four small plastic bags, each labeled with a cryptic set of letters and numbers and containing a thick slice of a yellow tomato. Scanning a QR code with my phone led me to an online survey with questions about each tomato’s balance of acidity and sweetness, texture and overall flavor. As I chewed on the slice from the bag marked “d86,” I noted the firm, almost meaty texture. Lacking the wateriness of a typical supermarket tomato, it would hold up beautifully in a salad or on a burger, I thought. And most importantly, it was tasty." +
                    "I learned later that this tomato had been dry-farmed, a form of agriculture that doesn’t require irrigation. Dry farming has roots stretching back millennia. But in the western United States, the practice largely fell out of widespread use in the 20th century.\n" +
                    "\n" +
                    "Today, however, farmers in the West are once again experimenting with dry farming as they grapple with water shortages, which are being exacerbated by rising temperatures and more frequent and intense droughts linked to climate change.\n" +
                    "\n" +
                    "Finding a more sustainable way to grow food in a thirsty state like California, for example, where agriculture accounts for roughly 80 percent of water usage and where a third of U.S. vegetables are grown, is a top priority. Dry farming won’t solve all of agriculture’s woes, but it offers a way forward, particularly for smaller-scale producers, while drawing less on a scarce natural resource. And even though the practice isn’t without limitations — dry-farmed produce tends to be physically smaller, and harvests are less bountiful overall — its benefits extend beyond water savings: Dry farming can also yield longer-lasting and better-tasting produce.",
            "By Erin Garcia de Jesús\n" +
                    "\n" +
                    "MARCH 2, 2023 AT 2:00 PM\n" +
                    "\n" +
                    "A new biological mashup just dropped.\n" +
                    "\n" +
                    "“Pikobodies,” bioengineered immune system proteins that are part plant and part animal, could help flora better fend off diseases, researchers report in the March 3 Science. The protein hybrids exploit animals’ uniquely flexible immune systems, loaning plants the ability to fight off emerging pathogens.\n" +
                    "\n" +
                    "Flora typically rely on physical barriers to keep disease-causing microbes at bay. If something unusual makes it inside the plants, internal sensors sound the alarm and infected cells die. But as pathogens evolve ways to dodge these defenses, plants can’t adapt in real time. Animals’ adaptive immune systems can, making a wealth of antibodies in a matter of weeks when exposed to a pathogen." +
                    "In a proof-of-concept study, scientists genetically modified one plant’s internal sensor to sport animal antibodies. The approach harnesses the adaptive immune system’s power to make almost unlimited adjustments to target invaders and lends it to plants, says plant immunologist Xinnian Dong, a Howard Hughes Medical Institute investigator at Duke University who was not involved in the work.\n" +
                    "\n" +
                    "Crops especially could benefit from having more adaptable immune systems, since many farms grow fields full of just one type of plant, says Dong. In nature, diversity can help protect vulnerable plants from disease-spreading pathogens and pests. A farm is more like a buffet.\n" +
                    "\n" +
                    "Researchers have had success fine-tuning plant genes to be disease-resistant, but finding the right genes and editing them can take more than a decade, says plant pathologist Sophien Kamoun of the Sainsbury Laboratory in Norwich, England. He and colleagues wanted to know if plant protection could get an additional boost from animal-inspired solutions. \n" +
                    "\n" +
                    "To create the pikobodies, the team fused small antibodies from llamas and alpacas with a protein called Pik-1 that’s found on the cells of Nicotiana benthamiana, a close relative of tobacco plants. Pik-1 typically detects a protein that helps a deadly blast fungus infect plants (SN: 7/10/17). For this test, the animal antibodies had been engineered to target fluorescent proteins\n" +
                    "\n" +
                    "Plants with the pikobodies killed cells exposed to the fluorescent proteins, resulting in dead patches on leaves, the team found. Of 11 tested versions, four were not toxic to the leaves and triggered cell death only when the pikobodies attached to the specific protein that they had been designed bind.  \n" +
                    "\n" +
                    "What’s more, pikobodies can be combined to give plants more than one way to attack a foreign invader. That tactic could be useful to hit pathogens with the nimble ability to dodge some immune responses from multiple angles.\n" +
                    "\n" +
                    "Theoretically, it’s possible to make pikobodies “against virtually any pathogen we study,” Kamoun says. But not all pikobody combos worked together in tests. “It’s a bit hit or miss,” he says. “We need some more basic knowledge to improve the bioengineering.”",
            "By Sid Perkins\n" +
                    "\n" +
                    "FEBRUARY 28, 2023 AT 7:01 PM\n" +
                    "\n" +
                    "The oldest known fossils of pollen-laden insects are of earwig-like ground-dwellers that lived in what is now Russia about 280 million years ago, researchers report. Their finding pushes back the fossil record of insects transporting pollen from one plant to another, a key aspect of modern-day pollination, by about 120 million years.\n" +
                    "\n" +
                    "The insects — from a pollen-eating genus named Tillyardembia first described in 1937 — were typically about 1.5 centimeters long, says Alexander Khramov, a paleoentomologist at the Borissiak Paleontological Institute in Moscow. Flimsy wings probably kept the creatures mostly on the forest floor, he says, leaving them to climb trees to find and consume their pollen.\n" +
                    "\n" +
                    "Recently, Khramov and his colleagues scrutinized 425 fossils of Tillyardembia in the institute’s collection. Six had clumps of pollen grains trapped on their heads, legs, thoraxes or abdomens, the team reports February 28 in Biology Letters. A proportion that small isn’t surprising, Khramov says, because the fossils were preserved in what started out as fine-grained sediments. The early stages of fossilization in such material would tend to wash away pollen from the insects’ remains." +
                    "The pollen-laden insects had only a couple of types of pollen trapped on them, the team found, suggesting that the critters were very selective in the tree species they visited. “That sort of specialization is in line with potential pollinators,” says Michael Engel, a paleoentomologist at the University of Kansas in Lawrence who was not involved in the study. “There’s probably vast amounts of such specialization that occurred even before Tillyardembia, we just don’t have evidence of it yet.”\n" +
                    "\n" +
                    "Further study of these fossils might reveal if Tillyardembia had evolved special pollen-trapping hairs or other such structures on their bodies or heads, says Conrad Labandeira, a paleoecologist at the National Museum of Natural History in Washington, D.C., also not part of the study. It would also be interesting, he says, to see if something about the pollen helped it stick to the insects. If the pollen grains had structures that enabled them to clump more readily, for example, then those same features may have helped them grab Velcro-like onto any hairlike structures on the insects’ bodies.","By Anna Gibbs\n" +
                    "\n" +
                    "SEPTEMBER 27, 2022 AT 7:00 AM" +
                    "The coral reef, once bustling with more than 5,000 long-spined sea urchins, became a ghost town in a matter of days. White skeletons with dangling spines dotted the reef near the Dutch Caribbean island of Saba, the water cloudy from the disintegrating corpses. In just a week last April, half of the urchins, Diadema antillarum, in a section of reef called “Diadema City” had died. In June, only 100 remained.\n" +
                    "\n" +
                    "The mysterious die-off started sweeping across the Caribbean in February. It’s eerily similar to a mass mortality event in 1983 that wiped out as much as 99 percent of the Caribbean Diadema population — a huge blow to not only the urchins, which have not fully recovered four decades later, but also the reefs. Without urchins grazing, algae can overwhelm a reef, damaging adult coral and leaving nowhere for new coral to settle." +
                    "Before the die-off, Saba’s coral cover — the part of a reef that consists of live hard coral rather than sponges, algae or other organisms — hovered around 50 percent. Today, that number is down to 3 percent.\n" +
                    "\n" +
                    "“It’s just downhill, downhill, downhill,” says Alwin Hylkema, a marine ecologist at Van Hall Larenstein University of Applied Sciences and Wageningen University in the Netherlands who is based in Saba (pronounced “say-bah”).\n" +
                    "\n" +
                    "I learned about Saba’s sea urchin problem only shortly after I learned that the island existed. Saba is a blip in the Caribbean; at 13 square kilometers, it’s about a quarter the size of Manhattan, with the towering Mount Scenery volcano at its center. Its reefs attract scuba divers, but a lack of beaches shields it from regular Caribbean tourist traffic — hence its nickname, “the unspoiled queen.” What the island lacks in size and sand it makes up for with its great variety of species, its biodiversity. Steep cliffs support several micro\u00ADclimates. In just a few hours, a visitor can hike from volcanic rock to grassy field to misty cloud forest.\n" +
                    "\n" +
                    "This diversity makes Saba the perfect spot for Sea & Learn, an annual educational program that brings scientists from around the world to the island. Former dive shop owner Lynn Costenaro launched the program in 2003 to encourage more divers to visit Saba during the off-season. But the event has grown to play an important role in educating the island’s 2,000 residents about their home’s unique wildlife and ecosystems.\n" +
                    "\n" +
                    "Throughout October, the scientists present their research on everything from biology and geology to astronomy at restaurants and in plazas. Several researchers also host public research trips underwater and on shore, so attendees can see the lobsters, or rock formations or stars for themselves." +
                    "This kind of local engagement with the environment comes at a time when many species are at risk, and not just on Saba, says Severin Irl, an island ecologist at Goethe University Frankfurt in Germany. Although islands cover only about 7 percent of the world’s landmass, they are home to an estimated 20 percent of all species — and 75 percent of all documented extinctions.\n" +
                    "\n" +
                    "Some island species occupy only one island; others are spread out in small populations across several islands. Species within small populations can develop a very narrow, island-specific set of adaptations, which spells trouble when humans and invasive species arrive. Today, any given plant or animal on an island is 12 times as likely to go extinct as species on the mainland, Irl and an international group of researchers reported in the November 2021 Global Ecology and Conservation. And the decline is speeding up at an unprecedented rate. As biodiversity decreases, islands lose the complexity that helps keep the ecosystem stable and less vulnerable to disruptions, such as climate change.","By Geoffrey Kamadi\n" +
                    "\n" +
                    "SEPTEMBER 14, 2022 AT 7:00 AM\n" +
                    "\n" +
                    "On the fringe of Kenya’s Gazi village, 50 kilometers south of Mombasa, Mwatime Hamadi walks barefoot on a path of scorching-hot sand toward a thicket of trees that seem to float where the land meets the Indian Ocean. Behind her moves village life: Mothers carry babies on their backs while they hang laundry between palm trees, women sweep the floors of huts thatched with palm fronds and old men chat idly about bygone days under the shade of mango trees.\n" +
                    "\n" +
                    "Hamadi is on her way to Gazi Forest, a dense patch of mangroves along Gazi Bay that coastal residents see as vital to their future. Mangroves “play a crucial role in safeguarding the marine ecosystem, which in turn is important for fisheries we depend on for our livelihood,” she says as she reaches a boardwalk that snakes through the coastal wetland." +
                    "Hamadi is a tour guide with Gazi Ecotourism Ventures, a group dedicated to empowering women and their community through mangrove conservation. This group is part of a larger carbon offset project called Mikoko Pamoja that has taken root and is now being copied farther south on Kenya’s coastline and in Mozambique and Madagascar.\n" +
                    "\n" +
                    "Through Mikoko Pamoja, residents of Gazi and nearby Makongeni are cultivating an economic ecosystem that relies on efforts to preserve and restore the mangrove forests. Revenue from carbon credits sold plus the money Hamadi and others earn from ecotourism are split between salaries, project costs and village improvements to health care, sanitation, schools and more.\n" +
                    "\n" +
                    "Mikoko Pamoja, launched in 2013, is the world’s first mangrove\u00AD-driven carbon credit initiative. It earned the United Nations’ Equator Prize in 2017, awarded for innovative solutions to poverty that involve conservation and sustainable use of biodiversity.\n" +
                    "\n" +
                    "“The mangrove vegetation was a thriving, healthy ecosystem in precolonial times,” says Ismail Barua, Mikoko Pamoja’s chairperson. During British rule, which stretched from the 1890s to 1963, the colonial government issued licenses to private companies to export mangrove wood. They did this without community involvement, which led to poaching of trees. Even after Kenya gained independence, mangroves were an important source of timber and fuel for industrial processes, main drivers of extensive destruction of the forests.\n" +
                    "\n" +
                    "Today, mangrove restoration is helping the region enter a new chapter, one where labor and resources are well-managed by local communities instead of being exploited. “The community is now able to run its own affairs,” Barua notes. Through innovative solutions and hard work, he says, “we’re trying to bring back a semblance of that ecosystem.”" +
                    "The dominant mangrove species in Gazi Forest is Rhizophora murcronata. With oval, leathery leaves about the size of a child’s palm and spindly branches that reach to the sun, the trees can grow up to 27 meters tall. Their interlaced roots, which grow from the base of the trunk into the salt\u00ADwater, make these evergreen trees unique.\n" +
                    "\n" +
                    "Salt kills most plants, but mangrove roots separate freshwater from salt for the tree to use. At low tide, the looping roots act like stilts and buttresses, keeping trunks and branches above the waterline and dry. Speckling these roots are thousands of specialized pores, or lenticels. The lenticels open to absorb gases from the atmosphere when exposed, but seal tight at high tide, keeping the mangrove from drowning.\n" +
                    "\n" +
                    "The thickets of roots also prevent soil erosion and buffer coastlines against tropical storms. Within these roots and branches, shorebirds and fish — and in some places, manatees and dolphins — thrive.\n" +
                    "\n" +
                    "Mangrove roots support an ecosystem that stores four times as much carbon as inland forests. That’s because the saltwater slows decomposition of organic matter, says Kipkorir Lang’at, a principal scientist at the Kenya Marine and Fisheries Research Institute, or KMFRI. So when mangrove plants and animals die, their carbon gets trapped in thick soils. As long as mangroves stay standing, the carbon stays in the soil.\n" +
                    "\n" +
                    "Robust estimates of mangrove forest area in Kenya before 1980 are not available, Lang’at says. However, with the clear-cutting of mangrove forests in Gazi Bay in the 1970s, he says, the area was left with vast expanses of bare, sandy coast.\n" +
                    "\n" +
                    "Other parts of the country experienced similar losses: Kenya lost up to 20 percent of its mangrove forests between 1985 and 2009 because no mechanism existed for their protection. The losses had a steep price: Just as mangroves absorb more carbon than inland forests, when destroyed, they release more carbon than other forests. And since the mangroves provided habitat and shelter for fish, their destruction meant that fishers were catching less.","By Sid Perkins\n" +
                    "\n" +
                    "SEPTEMBER 6, 2022 AT 12:00 PM\n" +
                    "\n" +
                    "When it comes to hoisting water, plants are real power lifters.\n" +
                    "\n" +
                    "For a tall tree, slurping hundreds of liters of water each day up to its leaves or needles, where photosynthesis takes place, can be quite a haul. Even for short grasses and shrubs, rising sap must somehow overcome gravity and resistance from plant tissues. Now, a first-of-its-kind study has estimated the power and energy needed to lift sap to plants’ foliage worldwide — and it’s a prodigious amount.\n" +
                    "\n" +
                    "Evaporation of water from foliage drives the suction that pulls sap upward, says Quetin, of the University of California, Santa Barbara (SN: 3/24/22). To estimate the total evaporative power for all plants on Earth annually, the team divided up a map of the world’s land area into cells that span 0.5° of latitude by 0.5° of longitude and analyzed data for the mix of plants in each cell that were actively pumping sap each month." +
                    "Over the course of a year, the world’s plants harness an average of 0.03 watts per square meter of sap-pumping power, climatologist Gregory Quetin and colleagues report August 17 in the Journal of Geophysical Research: Biogeosciences. Tree-rich areas, especially in the rainforests of the tropics, use the most power — 0.6 watts per square meter on average. That translates to about 90 percent of the amount of hydroelectric energy produced worldwide in 2019." +
                    "If plants in forest ecosystems had to tap their own energy stores rather than rely on evaporation to pump sap, they’d need to expend about 14 percent of the energy they generated via photosynthesis, the researchers found. Grasses and other plants in nonforest ecosystems would need to expend just over 1 percent of their energy stores, largely because such plants are far shorter and have less resistance to the flow of sap within their tissues than woody plants do.","By Rachel Crowell\n" +
                    "\n" +
                    "JULY 12, 2022 AT 11:56 AM\n" +
                    "\n" +
                    "Bees that land on short, wide flowers can fly away with an upset stomach.  \n" +
                    "\n" +
                    "Common eastern bumblebees (Bombus impatiens) are more likely to catch a diarrhea-inducing gut parasite from purple coneflowers, black-eyed Susans and other similarly shaped flora than other flowers, researchers report in the July Ecology. Because parasites and diseases contribute to bee decline, the finding could help researchers create seed mixes that are more bee-friendly and inform gardeners’ and land managers’ decisions about which flower types to plant." +
                    "The parasite (Crithidia bombi) is transmitted when the insects accidentally ingest contaminated bee feces, which “tends to make the bees dopey and lethargic,” says Rebecca Irwin, a community and evolutionary ecologist at North Carolina State University in Raleigh. “It isn’t the number one bee killer out there,” but bees sickened with it can struggle with foraging.    \n" +
                    "\n" +
                    "In laboratory experiments involving caged bees and 16 plant species, Irwin and her colleagues studied how different floral attributes affected transmission of the gut parasite. They focused on three factors of transmission: the amount of poop landing on flowers when bees fly and forage, how long the parasite survives on the plants and how easily the parasite is transmitted to new bees. Multiplied together, these three factors show the overall transmission rate.\n" +
                    "\n" +
                    "Compared with plants with long, narrow flowers like phlox and bluebeards, short, wide flowers had more feces land on them and transmitted the parasite more easily to the pollinators, increasing the overall parasite transmission rate for these flowers. However, parasite survival times were reduced on these blooms. This is probably due to the open floral shapes increased exposure to ultraviolet light, speeding the drying out of parasite-laden “fecal droplets,” Irwin says.\n" +
                    "\n" +
                    "The findings confirm a new theory suggesting that traits, such as flower shape, are better predictors of disease transmission than individual species of plants, says Scott McArt, an entomologist focusing on pollinator health at Cornell University who wasn’t involved with the study. Therefore, “you don’t need to know everything about every plant species when designing your pollinator-friendly garden or habitat restoration project.”\n" +
                    "\n" +
                    "Instead, to limit disease transmission among bees, it’s best to choose plants that have narrower, longer flowers, he says. “Wider and shorter flowers are analogous to the small, poorly ventilated rooms where COVID is efficiently transmitted among humans.”  \n" +
                    "\n" +
                    "If ripping out coneflowers or black-eyed Susans isn’t palatable, don’t fret. Irwin recommends continuing to plant a diversity of flower types. This helps if one type of flower is “a high transmitting species,” she notes. In the future, she plans to conduct field experiments examining other factors that could influence parasite transmission, such as whether bees are driven to visit certain types of flowers more often in nature. ","By Susan Milius\n" +
                    "\n" +
                    "APRIL 19, 2022 AT 9:00 AM\n" +
                    "\n" +
                    "Fake — and fatal — invitations to romance could be the newest bit of trickery uncovered among some jack-in-the-pulpit wildflowers.\n" +
                    "\n" +
                    "The fatal part isn’t the surprise. Jack-in-the-pulpits (Arisaema) are the only plants known to kill their own insect pollinators as a matter of routine, says evolutionary ecologist Kenji Suetsugu of Kobe University in Japan. The new twist, if confirmed, would be using sexual deception to woo pollinators into the death traps.\n" +
                    "\n" +
                    "Until now, biologists have found only three plant families with any species that pretend to offer sex to insects, Suetsugu says online March 28 in Plants, People, Planet. But unlike deceit in jack-in-the-pulpits, those other attractions aren’t fatal, just phony.\n" +
                    "\n" +
                    "The orchid family has turned out multiple cheats, some so seductive that a male insect leaves wasted sperm as well as pollen on a flower. Yet he doesn’t get even a sip of nectar (SN: 3/5/08; SN: 3/27/08). Similar scams have turned up among daisies: A few dark bumps that a human in bad light might mistake for an insect can drive male flies to frenzies on the yellow, orange or red Gorteria petals. Enthusiasm wanes with repeated disappointment though (SN: 1/29/14). And among irises, a species dangles velvety purple petals where deluded insects wallow." +
                    "Two jack-in-the-pulpit species in Japan have now raised suspicions that their family, the arums, should be added to the list of sexual cheats. To visually oriented humans, the 180 or so Arisaema species look like just a merry reminder of evolution’s endless weirdness. Some kind of flappy canopy, sometimes striped, bends over a little cupped “pulpit” with a pinkie-tip stub or mushroom bulge of plant flesh peeping over the rim. Below the rim, swaths of flowers open in succession — male blooms overtaken by flowers with female parts — as the plant grows from slim young jack to big mama.\n" +
                    "\n" +
                    "These oddball flowers depend mostly on pollinators that deserve a much bigger fan base: fungus gnats. These gnats, small as punctuation marks and hard to identify, are true flies. But don’t hold that against them. They don’t stalk picnic spreads or buzz-thump against windows. Pollinating gnats “are very frail,” Suetsugu says, and their wings make no noise a human can hear.\n" +
                    "\n" +
                    "Nor can a human always smell what draws fungus gnats. It’s clear, though, that the varied canopied pulpits can have a strong happy hour lure for those cruising pollinators looking to meet the right gnat. This will go terribly wrong.\n" +
                    "\n" +
                    "A tiny escape hatch deep in the trap stays open during the male phase of flowering, but that two-millimeter hole vanishes during the big mama stage. A gnat can’t overcome the slippery, flaking wax of the plant’s inner wall to climb out.  So any gnat tricked twice is doomed.\n" +
                    "\n" +
                    "Biologists had assumed that jack-in-the-pulpits seeking fungus gnats were perfuming the air with mushroomy, nice-place-to-have-kids scents. Many kinds seem to do so, but homey smells don’t explain an odd observation by Suetsugu and his colleagues. Of the important pollinator species for two Japanese jack-in-the-pulpits (A. angustatum and A. peninsulae), almost all the specks found in the traps were males.","By Jake Buehler\n" +
                    "\n" +
                    "MARCH 1, 2022 AT 11:00 AM\n" +
                    "\n" +
                    "Some plant roots draw a line in the sand — literally.\n" +
                    "\n" +
                    "In South Africa, you can move between cool, green forest and sunbaked shrubland in a single stride. These narrow borders between dramatically different ecosystems are maintained by intense competition between plants’ roots, new research suggests.\n" +
                    "\n" +
                    "Fynbos — a type of species-rich shrubland found only on the far southern tip of Africa — has the thinnest roots by far of any plant community in the world, researchers report in the March 1 Proceedings of the National Academy of Sciences. These nutrient-gobbling roots, plus some fire-encouraging adaptations, help turn the fynbos into an austere realm where only fynbos plants can survive. " +
                    "Interested in what factors organize nature at very broad scales, ecologist Lars Hedin and his colleagues wanted to look at places where environmental changes over time can flip the ecosystem between two distinct states. \n" +
                    "\n" +
                    "Enter the fynbos. It’s a low, shrubby habitat home to mind-blowing plant diversity: more than 7,000 species, most of which are found nowhere else in the world (SN: 8/24/2004). \n" +
                    "\n" +
                    "“It’s one of the most floristically biodiverse systems in the world,” says Hedin, of Princeton University. “It’s essentially as diverse as a tropical forest.”\n" +
                    "\n" +
                    "But right next to this hot, flower-filled wonderland — with the same climate and underlying geologic composition — is the lush Afro-temperate forest, flourishing with tall trees and moss, but fewer species overall. \n" +
                    "\n" +
                    "“The boundary is as sharp as one meter. It’s like a binary, zero to one transition,” says study coauthor Mingzhen Lu, an ecologist at the Santa Fe Institute in New Mexico. This is far narrower than the transition zone between savanna and tropical rainforest, he says, which can occur over many kilometers. \n" +
                    "\n" +
                    "To investigate what underlies these sharp borders, the team compared root samples from both fynbos and forest systems. They also conducted transplant experiments, moving Afro-temperate forest plants to the fynbos and tracking their growth over four years. The researchers kept fynbos plant roots away from the transplants and manipulated nutrient levels in some plots to unveil limiting factors for the forest plants.\n" +
                    "\n" +
                    "When kept away from fynbos roots or offered more nitrogen, forest trees grew five times as fast as those that experienced competition or nutritional disadvantage, the team found. That suggests the fynbos plants keep invading plants away by monopolizing nutrient access. \n" +
                    "\n","By Jude Coleman\n" +
                    "\n" +
                    "FEBRUARY 7, 2022 AT 9:00 AM\n" +
                    "\n" +
                    "Trillions of trees are growing on Earth, though how many kinds there are has been underestimated, a new study finds.\n" +
                    "\n" +
                    "Earth hosts roughly 64,100 known tree species. But there could be at least 73,300 — about 14 percent more than previously thought — a global collaboration of researchers reports in the Feb. 8 Proceedings of the National Academy of Sciences.\n" +
                    "\n" +
                    "More than a third of the 9,200 undiscovered species are probably rare and hiding out in South America’s biodiversity hot spots, such as the Amazon and tropical Andes, biologist Roberto Cazzolla Gatti of the University of Bologna in Italy and colleagues say.\n" +
                    "\n" +
                    "To estimate the number of Earth’s existing tree species, the team analyzed global forest data from two databases — the Global Forest Biodiversity Initiative and TREECHANGE. The researchers used a statistical analysis to account for the number of rare, infrequent trees that could be overlooked, revealing the new difference between documented species and novel ones.\n" +
                    "\n" +
                    "If more than 9,000 types of stationary, comparatively massive trees remain undetected, Cazzolla Gatti says, then the number of much smaller and more mobile animal species that are still unknown must be even greater.\n" +
                    "\n" +
                    "The research could help scientists target conservation efforts amid accelerating biodiversity loss worldwide (SN: 4/22/20). In vulnerable places such as the Amazon, where deforestation and fires are quickly erasing habitat, many plants and animals could be being wiped off the map before they are ever documented (SN: 9/1/21).\n" +
                    "\n" +
                    "Continuing to invest in conservation and preserving biodiversity is vital, Cazzolla Gatti says. Without it, “we have not many chances to keep our planet alive.” "
        )
        image = arrayOf(
            R.drawable.a1,
            R.drawable.a2,
            R.drawable.a3,
            R.drawable.a4,
            R.drawable.a5,
            R.drawable.a6,
            R.drawable.a7,
            R.drawable.a8,
            R.drawable.a9,
            R.drawable.a10
        )
        avatar = arrayOf(
            R.drawable.b1,
            R.drawable.b2,
            R.drawable.b3,
            R.drawable.b4,
            R.drawable.b5,
            R.drawable.b6,
            R.drawable.b7,
            R.drawable.b8,
            R.drawable.b9,
            R.drawable.b10
        )

        recyclerView = findViewById(R.id.recycerView)

        moveList = ArrayList()
        moveList.add(Article(image[0], title[0], name[0], day[0], avatar[0], descriptionArticles[0], detailArticles[0]))
        moveList.add(Article(image[1], title[1], name[1], day[1], avatar[1], descriptionArticles[1], detailArticles[1]))
        moveList.add(Article(image[2], title[2], name[2], day[2], avatar[2], descriptionArticles[2], detailArticles[2]))
        moveList.add(Article(image[3], title[3], name[3], day[3], avatar[3], descriptionArticles[3], detailArticles[3]))
        moveList.add(Article(image[4], title[4], name[4], day[4], avatar[4], descriptionArticles[4], detailArticles[4]))
        moveList.add(Article(image[5], title[5], name[5], day[5], avatar[5], descriptionArticles[5], detailArticles[5]))
        moveList.add(Article(image[6], title[6], name[6], day[6], avatar[6], descriptionArticles[6], detailArticles[6]))
        moveList.add(Article(image[7], title[7], name[7], day[7], avatar[7], descriptionArticles[7], detailArticles[7]))
        adapter = ArticleRecyclerAdapter(moveList, this@ArticlesActivity)

        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onClick(position: Int) {
        val intent = Intent(this@ArticlesActivity, ArticleDetailActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable(constant.ARTICLE, moveList[position])
        intent.putExtras(bundle)
        startActivity(intent)
    }

}