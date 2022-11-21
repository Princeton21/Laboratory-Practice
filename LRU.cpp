#include <bits/stdc++.h>
using namespace std;
int main() {
	cout << "Please Enter the number of frames\n";
	int frame_size;
	cin >> frame_size;

	list<int> dq;
	unordered_map<int, list<int>::iterator> hash;

	vector<int> ref_str;
	cout << "Enter the length of reference string\n";
	int ref_size;
	cin >> ref_size;
	cout << "Enter the reference string\n";
	int val;
	for (int i = 0; i < ref_size; ++i) {
		cin >> val;
		ref_str.push_back(val);
	}
	int hit = 0;
	int fault = 0;

	for (auto page : ref_str) {
		if (hash.find(page) == hash.end()) {
			fault++;
			if (dq.size() == frame_size) {
				hash.erase(dq.back());
				dq.pop_back();
			}
		} else {dq.erase(hash[page]); hit++;}

		dq.push_front(page);
		hash[page] = dq.begin();
	}
	cout << "The total number of hits is : " << hit << endl;
	cout << "The number of page faults is : " << fault << endl;
	cout << "The hit ratio is : " << (double)hit / (double)ref_size << endl;

}