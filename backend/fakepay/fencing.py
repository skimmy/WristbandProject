import math

# computes distance between two points (Haversine formula)
def distance(lat1, lon1, lat2, lon2):
    # translate degrees to radians
    lat1 = math.radians(lat1)
    lat2 = math.radians(lat2)
    lon1 = math.radians(lon1)
    lon2 = math.radians(lon2)
    dlat = lat2 - lat1
    dlon = lon2 - lon1
    # compute distance using Haversine formula
    a = math.sin(dlat/2)**2 + math.cos(lat1)* math.cos(lat2) * math.sin(dlon/2)**2
    c = 2 * math.asin(math.sqrt(a))
    # normalize on sphere radius
    r = 6371000 # earth radius in meters
    return c * r

# Check if the given location violates the given fence
def checkFence(location, fence):
    d = distance(location.latitude, location.longitude,
                 fence.latitude, fence.longitude)
    return (d > fence.radius)

# Check whether a given location violates given fences
#   location = (latitude, longitude)
#   fences = [(lat,lon,rad), (lat,lon,rad) ... ]
def checkFences(location, fences):
    output = []
    n = len(fences)
    for i in range(n):
        if checkFence(location, fences[i]):
            output.append(i)
    return output
