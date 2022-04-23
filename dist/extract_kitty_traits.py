from xml.dom.minidom import parse
import xml.dom.minidom
import os

svg_dir = "./cryptokitties/"
traits = ['tailFur', 'tailShadow', 'tail', 'bodyFur', 'shadow', 'highlight', 'bodyMask', 'body', 'eye', 'mouth']
item_ = ['item_j', 'item_i', 'item_h', 'item_g', 'item_f', 'item_e', 'item_d', 'item_c', 'item_b', 'item_a'] # empty traits

# run once for initialzation
def extract_trait_from_svg(file_name):
    DOMTree = xml.dom.minidom.parse(svg_dir+file_name)
    collection = DOMTree.documentElement
    groups = collection.getElementsByTagName("g")

    for g in groups:
        if g.hasAttribute("id"):
            trait = g.getAttribute("id")
            if trait not in traits:
                traits.append(trait)
            if trait == 'item_a' or trait == 'item_b' or trait == 'item_c' or trait == 'item_d' or trait == 'item_e' or trait == 'item_f' or trait == 'item_g' or trait == 'item_h' or trait == 'item_i' or trait == 'item_j':
                if file_name not in item_:
                    item_.append(file_name)

'''
for root, dirs, files in os.walk(svg_dir):
    for svg in files:
        print("extracting:", svg)
        extract_trait_from_svg(svg)

print(traits)
print(item_)
'''

trait_segment = {}

def extract_trait_segment_from_svg(file_name):
    DOMTree = xml.dom.minidom.parse(svg_dir+file_name)
    collection = DOMTree.documentElement
    groups = collection.getElementsByTagName("g")

    for g in groups:
        if g.hasAttribute("id"):
            trait = g.getAttribute("id")
            if trait in traits:
                if trait not in trait_segment:
                    trait_segment[trait] = [g.toxml()]
                else:
                    if g.toxml() not in trait_segment[trait]:
                        trait_segment[trait].append(g.toxml())


for root, dirs, files in os.walk(svg_dir):
    for svg in files:
        print("extracting segment:", svg)
        extract_trait_segment_from_svg(svg)

save_dir = "./traits/"

for trait in trait_segment:
    print(trait, len(trait_segment[trait]))
    trait_dir = save_dir + trait
    if os.path.isdir(trait_dir) is False:
        os.mkdir(trait_dir)
    for i in range(len(trait_segment[trait])):
        with open(trait_dir + "/" + str(i) + "." + trait, "w+") as f:
            f.write(trait_segment[trait][i])

'''
tailFur 894
tailShadow 3
tail 4
bodyFur 1690
shadow 6
highlight 6
body 4
eye 59
mouth 17
bodyMask 2
'''