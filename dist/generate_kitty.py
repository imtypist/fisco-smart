import random

traits = {'tailFur': 894, 'tailShadow': 3, 'tail': 4, 'bodyFur': 1690, 'shadow': 6, 'highlight': 6, 'bodyMask': 2, 'body': 4, 'eye': 59, 'mouth': 17}
traits_base_dir = "./traits/"

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

random_kitty = '<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 3000 3000">'

for trait in traits:
    random_trait = random.randint(0, traits[trait]-1)
    with open(traits_base_dir + trait + "/" + str(random_trait) + "." + trait, "r") as f:
        random_kitty += f.read()

random_kitty += "</svg>"

with open("random_kitty.svg","w+") as f:
    f.write(random_kitty)