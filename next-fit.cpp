#include<bits/stdc++.h>
using namespace std;
int main() {
	cout << "Enter the number of partitions : \n";
	int part_size;
	cin >> part_size;
	cout << "Enter the size of partitions : \n";
	vector<int> part(part_size);
	for (auto &p : part)cin >> p;
	cout << "Enter the no. of processes : \n";
	int pro_size;
	cin >> pro_size;
	cout << "Enter the size of processes : \n";
	vector<int> pro(pro_size);
	for (auto &p : pro) cin >> p;

	vector<int> filled(part_size);
	for (int i = 0; i < pro_size; i++) {
		int k = -1;
		for (int j = 0; j < part_size; j++) {
			if (part[j] >= pro[i])
				if (filled[j] != 1) {
					k = j;
					filled[k] = 1;
					break;
				}
		}
		if (k == -1) cout << "\nFirst fit not found for process " << i + 1;
		else cout << "\nFirst fit for process " << i + 1 << " is " << part[k] << " and Hole of " << part[k] - pro[i] << " is created.";
	}
}

